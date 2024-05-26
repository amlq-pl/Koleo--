package pl.tcs.oopproject.viewmodel.tickets;

import pl.tcs.oopproject.viewmodel.discount.DiscountInterface;
import pl.tcs.oopproject.viewmodel.discount.PriceInterface;
import pl.tcs.oopproject.viewmodel.discount.PricePLN;
import pl.tcs.oopproject.viewmodel.station.Station;

import java.time.LocalDateTime;

public interface TicketInterface {
	PriceInterface getCost();
	DiscountInterface getDiscount();
	DiscountInterface getOneTimeDiscount();
	String getID();
	LocalDateTime getPurchaseDate();
}