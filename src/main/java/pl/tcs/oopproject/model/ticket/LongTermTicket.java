package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.discount.Discount;
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
	private final int id;
	private boolean returned;
	
	public LongTermTicket(LocalDate startDate, LongTermTicketType longTermTicketType, Discount discount, OneTimeDiscount oneTimeDiscount, int id) {
		this.startDate = startDate;
		purchaseDate = LocalDateTime.now();
		this.longTermTicketType = longTermTicketType;
		this.discount = discount;
		this.oneTimeDiscount = oneTimeDiscount;
		this.id = id;
		returned = false;
	}
	
	@Override
	public PriceInterface getCost() {
		return longTermTicketType.cost();
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
	public boolean returned() {
		return returned;
	}
	
	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}
	
	@Override
	public boolean returnTicket() {
		if(returned) return false;
		returned = true;
		return true;
	}
}
