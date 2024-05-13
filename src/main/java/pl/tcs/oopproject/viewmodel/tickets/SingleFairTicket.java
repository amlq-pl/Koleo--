package pl.tcs.oopproject.viewmodel.tickets;

import pl.tcs.oopproject.viewmodel.carriage.CarriageClassType;
import pl.tcs.oopproject.viewmodel.carriage.CarriageInterface;
import pl.tcs.oopproject.viewmodel.carriage.CarriageType;
import pl.tcs.oopproject.viewmodel.connection.ConnectionWithTransfers;
import pl.tcs.oopproject.viewmodel.connection.DirectConnection;
import pl.tcs.oopproject.viewmodel.discount.Discount;
import pl.tcs.oopproject.viewmodel.discount.OneTimeDiscount;
import pl.tcs.oopproject.viewmodel.place.Place;
import pl.tcs.oopproject.viewmodel.place.SpecificSeat;
import pl.tcs.oopproject.viewmodel.seat.SeatInterface;
import pl.tcs.oopproject.viewmodel.seat.SeatType;
import pl.tcs.oopproject.viewmodel.station.Station;
import pl.tcs.oopproject.viewmodel.station.StationInterface;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SingleFairTicket implements TicketInterface{
	Place place;
	Discount discount;
	OneTimeDiscount oneTimeDiscount;
	LocalDateTime purchaseDate;
	SingleFairTicket(Place place, Discount discount, OneTimeDiscount oneTimeDiscount) {
		this.place = place;
		this.discount = discount;
		this.oneTimeDiscount = oneTimeDiscount;
		purchaseDate = LocalDateTime.now();
	}
	
	@Override
	public int getCost() {
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
	public StationInterface getDepartureStation() {
		return place.getConnection().getFirstStation();
	}
	
	public StationInterface getArrivalStation() {
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
		for(DirectConnection connection : place.getConnection().getTrains()) {
			trains.add(connection.getNumber());
		}
		return trains;
	}
}
