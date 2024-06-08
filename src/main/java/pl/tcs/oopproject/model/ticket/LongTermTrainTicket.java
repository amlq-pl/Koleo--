package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.discount.Price;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.postgresDatabaseIntegration.CreateOrRefactor;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import static pl.tcs.oopproject.model.ticket.SingleJourneyTrainTicket.getPricePLN;

public class LongTermTrainTicket implements TrainTicket {
	private final LocalDate startDate;
	private final LocalDateTime purchaseDate;
	private final LongTermTicketType longTermTicketType;
	private final Discount appliedDiscount;
	private final Voucher appliedVoucher;
	private final int id;
	private final Person person;
	
	public LongTermTrainTicket(LocalDate startDate, LongTermTicketType longTermTicketType, Discount appliedDiscount, Voucher appliedVoucher, int id, Person person) {
		this.startDate = startDate;
		this.person = person;
		purchaseDate = LocalDateTime.now();
		this.longTermTicketType = longTermTicketType;
		this.appliedDiscount = appliedDiscount;
		this.appliedVoucher = appliedVoucher;
		this.id = id;
	}
	
	public Person getPerson() {
		return person;
	}
	@Override
	public Price cost() {
		return getPricePLN(longTermTicketType.cost(), appliedDiscount, appliedVoucher);
	}
	
	@Override
	public boolean isActive() {
		Period p = longTermTicketType.period();
		LocalDateTime afterPeriod = startDate.atStartOfDay().plusYears(p.getYears());
		afterPeriod = afterPeriod.plusMonths(p.getMonths());
		afterPeriod = afterPeriod.plusDays(p.getDays());
		
		return LocalDateTime.now().isAfter(startDate.atStartOfDay()) && LocalDateTime.now().isBefore(afterPeriod);
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
	public int id() {
		return id;
	}
	
	@Override
	public LocalDateTime purchaseDate() {
		return purchaseDate;
	}
}
