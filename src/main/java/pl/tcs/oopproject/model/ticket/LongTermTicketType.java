package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.discount.PriceInterface;

import java.time.Period;

public record LongTermTicketType(Period period, PriceInterface cost) {
	public void display() {
		System.out.println("period: " + period + " cost " + cost.getPriceValue());
	}
}
