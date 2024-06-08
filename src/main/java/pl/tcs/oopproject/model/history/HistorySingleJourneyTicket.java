package pl.tcs.oopproject.model.history;

import org.jetbrains.annotations.NotNull;
import pl.tcs.oopproject.model.assignedSeat.AssignedSeat;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Price;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.discount.PricePLN;
import pl.tcs.oopproject.model.assignedSeat.TrainsAssignedSeats;
import pl.tcs.oopproject.model.station.Station;
import pl.tcs.oopproject.model.ticket.Details;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.postgresDatabaseIntegration.CreateOrRefactor;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class HistorySingleJourneyTicket{
	public static @NotNull PricePLN getPricePLN(Price cost2, Discount appliedDiscount, Voucher appliedVoucher) {
		double cost = cost2.value();
		if(appliedDiscount != null)
			cost = cost * (100 - appliedDiscount.value()) / 100;
		if(appliedVoucher != null)
			cost = cost * (100 - appliedVoucher.value()) / 100;
		return new PricePLN(cost);
	}
	
	private final static int ticketValidityWindow = 8; //how many hours after planned arrival is ticket active
	private final AssignedSeat assignedSeats;
	private final LocalDateTime departureTime;
	private final LocalDateTime arrivalTime;
	private final String departureStation;
	private final String arrivalStation;
	
	private final int id;
	private final Details details;
	private boolean refunded;
	private final Person person;
	PricePLN cost;
	
	public HistorySingleJourneyTicket(Discount appliedDiscount,
	                                  Voucher appliedVoucher,
	                                  AssignedSeat assignedSeats,
	                                  LocalDateTime departureTime,
	                                  LocalDateTime arrivalTime,
	                                  String arrivalStation,
	                                  int id,
	                                  Details details,
	                                  String departureStation,
	                                  Person person,
	                                  double cost,
	                                  boolean refunded) {
		this.assignedSeats = assignedSeats;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.arrivalStation = arrivalStation;
		this.id = id;
		this.details = details;
		this.departureStation = departureStation;
		this.person = person;
		this.refunded = false;
		this.cost = getPricePLN(new PricePLN(cost), appliedDiscount, appliedVoucher);
	}
	
	public Person person() {
		return person;
	}

	public PricePLN cost() {
		return cost;
	}

	public boolean isActive() {
		return LocalDateTime.now().isBefore(arrivalTime().plusHours(ticketValidityWindow));
	}
	
	public boolean refunded() {
		return refunded;
	}
	
	public boolean refundTicket() throws SQLException {
		CreateOrRefactor refactor = new CreateOrRefactor();
		if (refunded()) return false;
		refactor.returnSingleJourneyTrainTicket(id);
		return true;
	}
	public int id() {
		return id;
	}
	
	public String departureStation() {
		return departureStation;
	}
	
	public String arrivalStation() {
		return arrivalStation;
	}
	
	public LocalDateTime departureTime() {
		return departureTime;
	}
	
	public LocalDateTime arrivalTime() {
		return arrivalTime;
	}
	
	public Details details() {
		return details;
	}
	
	public AssignedSeat places() {
		return assignedSeats;
	}
}