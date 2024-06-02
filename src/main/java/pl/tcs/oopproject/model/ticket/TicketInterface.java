package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.DiscountInterface;
import pl.tcs.oopproject.model.discount.OneTimeDiscount;
import pl.tcs.oopproject.model.discount.PriceInterface;

import java.time.LocalDateTime;

public interface TicketInterface {
	PriceInterface getCost();
	
	DiscountInterface getDiscount();
	
	DiscountInterface getOneTimeDiscount();
	
	boolean returned();
	
	boolean returnTicket();
	
	int getID();
	
	LocalDateTime getPurchaseDate();
}