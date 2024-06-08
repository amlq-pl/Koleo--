package pl.tcs.oopproject.model.ticket;

import org.jetbrains.annotations.NotNull;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Price;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.discount.PricePLN;
import pl.tcs.oopproject.model.assignedSeat.TrainsAssignedSeats;
import pl.tcs.oopproject.model.station.Station;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.postgresDatabaseIntegration.CreateOrRefactor;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class SingleJourneyTrainTicket implements TrainTicket {
	private final static int ticketValidityWindow = 8; //how many hours after planned arrival is ticket active
	private final TrainsAssignedSeats trainsAssignedSeats;
	private final Discount appliedDiscount;
	private final Voucher appliedVoucher;
	private final LocalDateTime purchaseDate;
	private final int id;
	private final Details details;
	private boolean returned;
	private final MultiStopRoute train;
	private final Person person;
	
	public SingleJourneyTrainTicket(TrainsAssignedSeats trainsAssignedSeats, Discount appliedDiscount, Voucher appliedVoucher, int id, Details details, MultiStopRoute train, Person person) {
		this.trainsAssignedSeats = trainsAssignedSeats;
		this.appliedDiscount = appliedDiscount;
		this.appliedVoucher = appliedVoucher;
		this.id = id;
		this.details = details;
		this.train = train;
		this.person = person;
		purchaseDate = LocalDateTime.now();
		returned = false;
	}
	
	public Person getPerson() {
		return person;
	}
	
	@Override
	public PricePLN cost() {
		return getPricePLN(train.cost(), appliedDiscount, appliedVoucher);
	}
	
	@NotNull
	static PricePLN getPricePLN(Price cost2, Discount appliedDiscount, Voucher appliedVoucher) {
		double cost = cost2.value();
		if(appliedDiscount != null)
			cost = cost * (100 - appliedDiscount.value()) / 100;
		if(appliedVoucher != null)
			cost = cost * (100 - appliedVoucher.value()) / 100;
		return new PricePLN(cost);
	}
	
	@Override
	public boolean isActive() {
		return LocalDateTime.now().isBefore(arrivalTime().plusHours(ticketValidityWindow));
	}
	
	@Override
	public Discount appliedDiscount() {
		return appliedDiscount;
	}
	
	@Override
	public Voucher appliedVoucher() {
		return appliedVoucher;
	}
	
	@Override
	public boolean refunded() {
		return returned;
	}
	
	@Override
	public boolean refundTicket() throws SQLException {
		CreateOrRefactor refactor = new CreateOrRefactor();
		if (refunded()) return false;
		refactor.returnSingleJourneyTrainTicket(id);
		return true;
	}
	
	@Override
	public int id() {
		return id;
	}
	
	@Override
	public LocalDateTime purchaseDate() {
		return purchaseDate;
	}
	
	public Station departureStation() {
		return trainsAssignedSeats.getConnection().originStation();
	}
	
	public Station arrivalStation() {
		return trainsAssignedSeats.getConnection().destinationStation();
	}
	
	public LocalDateTime departureTime() {
		return departureStation().departureTime();
	}
	
	public LocalDateTime arrivalTime() {
		return arrivalStation().arrivalTime();
	}
	
	public Details details() {
		return details;
	}
	
	public TrainsAssignedSeats places() {
		return trainsAssignedSeats;
	}
}
