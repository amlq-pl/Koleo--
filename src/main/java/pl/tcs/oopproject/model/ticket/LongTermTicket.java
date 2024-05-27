package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.DiscountInterface;
import pl.tcs.oopproject.model.discount.OneTimeDiscount;
import pl.tcs.oopproject.model.discount.PriceInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LongTermTicket implements TicketInterface {
	private final LocalDate startDate;
	private final LocalDateTime purchaseDate;
	private final LongTermTicketType longTermTicketType;
	private final Discount discount;
	private final OneTimeDiscount oneTimeDiscount;
	private final String id;
	
	public LongTermTicket(LocalDate startDate, LongTermTicketType longTermTicketType, Discount discount, OneTimeDiscount oneTimeDiscount, String id) {
		this.startDate = startDate;
		purchaseDate = LocalDateTime.now();
		this.longTermTicketType = longTermTicketType;
		this.discount = discount;
		this.oneTimeDiscount = oneTimeDiscount;
		this.id = id;
	}
	
	@Override
	public PriceInterface getCost() {
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
