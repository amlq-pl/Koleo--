package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.discount.Price;
import pl.tcs.oopproject.model.discount.PricePLN;

import java.time.Period;

public record LongTermTicketType(Period period, PricePLN cost, String company) {
	
	@Override
	public String toString() {
		int years = period.getYears();
		int months = period.getMonths();
		int days = period.getDays();
		
		String readableString = years + " R, " + months + " M, " + days + " D";
		return "okres " + readableString +  "\n koszt " + cost.value() + "\n //przewoźnik " + company;
	}
}
