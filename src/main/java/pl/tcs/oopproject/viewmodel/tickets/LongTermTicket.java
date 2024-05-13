package pl.tcs.oopproject.viewmodel.tickets;

import pl.tcs.oopproject.viewmodel.discount.Discount;
import pl.tcs.oopproject.viewmodel.discount.DiscountInterface;
import pl.tcs.oopproject.viewmodel.discount.OneTimeDiscount;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LongTermTicket implements TicketInterface {
	LocalDate startDate;
	LocalDateTime purchaseDate;
	LongTermTicketType longTermTicketType;
	Discount discount;
	OneTimeDiscount oneTimeDiscount;
	String id;
	
	LongTermTicket(LocalDate startDate, LongTermTicketType longTermTicketType, Discount discount, OneTimeDiscount oneTimeDiscount, String id) {
		this.startDate = startDate;
		purchaseDate = LocalDateTime.now();
		this.longTermTicketType = longTermTicketType;
		this.discount = discount;
		this.oneTimeDiscount = oneTimeDiscount;
		this.id = id;
	}
	
	@Override
	public int getCost() {
		return longTermTicketType.getCost();
	}
	
	@Override
	public DiscountInterface getDiscount() {
		return discount;
	}
	
	@Override
	public DiscountInterface getOneTimeDiscount() {
		return oneTimeDiscount;
	}
	
	@Override
	public String getID() {
		return id;
	}
	
	@Override
	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}
}
