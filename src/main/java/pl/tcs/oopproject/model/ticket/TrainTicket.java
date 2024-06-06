package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Price;
import pl.tcs.oopproject.model.discount.Voucher;

import java.time.LocalDateTime;

public interface TrainTicket {
	Price cost();
	boolean isActive();
	Discount appliedDiscount();
	
	Voucher appliedVoucher();
	
	boolean returned();
	
	boolean returnTicket();
	
	int id();
	
	LocalDateTime purchaseDate();
}