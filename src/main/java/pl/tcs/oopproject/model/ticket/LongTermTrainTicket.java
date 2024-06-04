package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.discount.Price;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LongTermTrainTicket implements TrainTicket {
	private final LocalDate startDate;
	private final LocalDateTime purchaseDate;
	private final LongTermTicketType longTermTicketType;
	private final Discount appliedDiscount;
	private final Voucher appliedVoucher;
	private final int id;
	private boolean returned;
	
	public LongTermTrainTicket(LocalDate startDate, LongTermTicketType longTermTicketType, Discount appliedDiscount, Voucher appliedVoucher, int id) {
		this.startDate = startDate;
		purchaseDate = LocalDateTime.now();
		this.longTermTicketType = longTermTicketType;
		this.appliedDiscount = appliedDiscount;
		this.appliedVoucher = appliedVoucher;
		this.id = id;
		returned = false;
	}
	
	@Override
	public Price cost() {
		return longTermTicketType.cost() ;
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
	public int id() {
		return id;
	}
	
	@Override
	public LocalDateTime purchaseDate() {
		return purchaseDate;
	}
	
	@Override
	public boolean returnTicket() {
		if (returned) return false;
		returned = true;
		return true;
	}
}
