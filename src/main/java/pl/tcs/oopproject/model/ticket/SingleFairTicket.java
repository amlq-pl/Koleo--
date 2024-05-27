package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.connection.DirectConnection;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.OneTimeDiscount;
import pl.tcs.oopproject.model.discount.PricePLN;
import pl.tcs.oopproject.model.place.Place;
import pl.tcs.oopproject.model.place.SpecificSeat;
import pl.tcs.oopproject.model.station.Station;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SingleFairTicket implements TicketInterface {
	private final Place place;
	private final Discount discount;
	private final OneTimeDiscount oneTimeDiscount;
	private final LocalDateTime purchaseDate;
	
	public SingleFairTicket(Place place, Discount discount, OneTimeDiscount oneTimeDiscount) {
		this.place = place;
		this.discount = discount;
		this.oneTimeDiscount = oneTimeDiscount;
		purchaseDate = LocalDateTime.now();
	}
	
	@Override
	public PricePLN getCost() {
		return place.getConnection().getCost();
	}
	
	@Override
	public Discount getDiscount() {
		return discount;
	}
	
	@Override
	public OneTimeDiscount getOneTimeDiscount() {
		return oneTimeDiscount;
	}
	
	@Override
	public String getID() {
		return null;
	}
	
	@Override
	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}
	
	public Station getDepartureStation() {
		return place.getConnection().getFirstStation();
	}
	
	public Station getArrivalStation() {
		return place.getConnection().getLastStation();
	}
	
	public LocalDateTime getDepartureTime() {
		return getDepartureStation().getDepartureTime();
	}
	
	public LocalDateTime getArrivalTime() {
		return getArrivalStation().getArrivalTime();
	}
	
	public List<SpecificSeat> getSeats() {
		return place.getSeat();
	}
	
	public List<Integer> getTrainNumber() {
		List<Integer> trains = new ArrayList<>();
		for (DirectConnection connection : place.getConnection().getTrains()) {
			trains.add(connection.getNumber());
		}
		return trains;
	}
}
