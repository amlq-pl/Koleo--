package pl.tcs.oopproject.model.history;

import org.jetbrains.annotations.NotNull;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Price;
import pl.tcs.oopproject.model.discount.PricePLN;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.ticket.LongTermTicketType;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.postgresDatabaseIntegration.CreateOrRefactor;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

public class HistoryLongTermTicket {
	public static @NotNull PricePLN getPricePLN(Price cost2, Discount appliedDiscount, Voucher appliedVoucher) {
		double cost = cost2.value();
		if(appliedDiscount != null)
			cost = cost * (100 - appliedDiscount.value()) / 100;
		if(appliedVoucher != null)
			cost = cost * (100 - appliedVoucher.value()) / 100;
		return new PricePLN(cost);
	}
	
	private final LocalDate startDate;
	private final LongTermTicketType longTermTicketType;
	private final int id;
	private boolean returned;
	private final Person person;
	private final PricePLN cost;
	
	public HistoryLongTermTicket(LocalDate startDate, LongTermTicketType longTermTicketType, Discount appliedDiscount, Voucher appliedVoucher, int id, Person person) {
		this.startDate = startDate;
		this.person = person;
		this.longTermTicketType = longTermTicketType;
		this.id = id;
		returned = false;
		cost = getPricePLN(longTermTicketType.cost(), appliedDiscount, appliedVoucher);
	}
	
	public Person getPerson() {
		return person;
	}

	public Price cost() {
		return cost;
	}
	
	public boolean isActive() {
		Period p = longTermTicketType.period();
		LocalDateTime afterPeriod = startDate.atStartOfDay().plusYears(p.getYears());
		afterPeriod = afterPeriod.plusMonths(p.getMonths());
		afterPeriod = afterPeriod.plusDays(p.getDays());
		
		return LocalDateTime.now().isAfter(startDate.atStartOfDay()) && LocalDateTime.now().isBefore(afterPeriod);
	}
	
	public boolean refunded() {
		return returned;
	}
	
	public int id() {
		return id;
	}
	
	public boolean refundTicket() throws SQLException {
		CreateOrRefactor refactor = new CreateOrRefactor();
		if (refunded()) return false;
		refactor.returnLongTermTrainTicket(id);
		returned = true;
		return true;
	}
}
