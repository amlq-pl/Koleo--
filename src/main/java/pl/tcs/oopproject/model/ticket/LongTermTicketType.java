package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.discount.Price;

import java.time.Period;

public record LongTermTicketType(Period period, Price cost, String company) {
	
	@Override
	public String toString() {
		return "period: " + period + " cost: " + cost.value();
	}
}
