package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.discount.Price;
import pl.tcs.oopproject.model.discount.PricePLN;

import java.time.Period;

public record LongTermTicketType(Period period, PricePLN cost, String company) {
	
	@Override
	public String toString() {
		return "okres " + period + "\n koszt: " + cost.value();
	}
}
