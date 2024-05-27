package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.discount.DiscountInterface;
import pl.tcs.oopproject.model.discount.PriceInterface;

import java.time.LocalDateTime;

public interface TicketInterface {
	PriceInterface getCost();
	
	DiscountInterface getDiscount();
	
	DiscountInterface getOneTimeDiscount();
	
	String getID();
	
	LocalDateTime getPurchaseDate();
}