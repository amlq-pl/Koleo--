package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Price;
import pl.tcs.oopproject.model.discount.Voucher;

import java.sql.SQLException;
import java.time.LocalDateTime;

public interface TrainTicket {
	Price cost() throws SQLException;
	boolean isActive();
	Discount appliedDiscount();
	
	Voucher appliedVoucher();
	
	int id();
	
	LocalDateTime purchaseDate();
}