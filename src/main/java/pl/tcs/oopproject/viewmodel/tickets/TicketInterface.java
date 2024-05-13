package pl.tcs.oopproject.viewmodel.tickets;

import pl.tcs.oopproject.viewmodel.discount.DiscountInterface;
import pl.tcs.oopproject.viewmodel.station.Station;

import java.time.LocalDateTime;

public interface TicketInterface {
	public int getCost();
	public DiscountInterface getDiscount();
	public DiscountInterface getOneTimeDiscount();
	public String getID();
	public LocalDateTime getPurchaseDate();
}