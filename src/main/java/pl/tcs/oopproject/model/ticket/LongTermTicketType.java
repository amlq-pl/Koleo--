package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.discount.Price;

import java.time.Period;

public record LongTermTicketType(Period period, Price cost) {
	public void display() {
		System.out.println("period: " + period + " cost " + cost.value());
	}
}
