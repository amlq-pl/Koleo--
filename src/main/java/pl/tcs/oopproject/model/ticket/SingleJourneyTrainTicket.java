package pl.tcs.oopproject.model.ticket;

import org.jetbrains.annotations.NotNull;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.connection.ScheduledTrain;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Price;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.discount.PricePLN;
import pl.tcs.oopproject.model.place.TrainsAssignedSeats;
import pl.tcs.oopproject.model.place.AssignedSeat;
import pl.tcs.oopproject.model.station.Station;
import pl.tcs.oopproject.model.users.Person;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SingleJourneyTrainTicket implements TrainTicket {
	private final static int ticketValidityWindow = 8; //how many hours after planned arrival is ticket active
	private final ArrayList<TrainsAssignedSeats> trainsAssignedSeats;
	private final Discount appliedDiscount;
	private final Voucher appliedVoucher;
	private final LocalDateTime purchaseDate;
	private final int id;
	private final Details details;
	private boolean returned;
	
	private final MultiStopRoute train;
	private final Person person;
	
	public SingleJourneyTrainTicket(ArrayList<TrainsAssignedSeats> trainsAssignedSeats, Discount appliedDiscount, Voucher appliedVoucher, int id, Details details, MultiStopRoute train, Person person) {
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
	public boolean returned() {
		return returned;
	}
	
	@Override
	public boolean returnTicket() {
		if (returned) return false;
		returned = true;
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
		return trainsAssignedSeats.get(0).getConnection().originStation();
	}
	
	public Station arrivalStation() {
		return trainsAssignedSeats.get(0).getConnection().destinationStation();
	}
	
	public LocalDateTime departureTime() {
		return departureStation().departureTime();
	}
	
	public LocalDateTime arrivalTime() {
		return arrivalStation().arrivalTime();
	}
	
	
	public List<Integer> trainNumber() {
		List<Integer> trains = new ArrayList<>();
		for (ScheduledTrain connection : trainsAssignedSeats.get(0).getConnection().trains()) {
			trains.add(connection.getNumber());
		}
		return trains;
	}
	
	public Details details() {
		return details;
	}
	
	public List<TrainsAssignedSeats> places() {
		return trainsAssignedSeats;
	}
}
