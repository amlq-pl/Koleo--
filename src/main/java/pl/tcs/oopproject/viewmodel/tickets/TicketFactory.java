package pl.tcs.oopproject.viewmodel.tickets;

import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.OneTimeDiscount;
import pl.tcs.oopproject.model.ticket.LongTermTicket;
import pl.tcs.oopproject.model.ticket.LongTermTicketType;
import pl.tcs.oopproject.model.ticket.SingleFairTicket;

import java.time.LocalDate;

public class TicketFactory {
	
	public LongTermTicket createLongTermTicket(LongTermTicketType ticketType, Discount discount, OneTimeDiscount oneTimeDiscount, LocalDate startDate) {
		String id = null; // = SAVE DATA IN DATABASE, FUNCTION MUST RETURN ID OF THE ORDER
		return new LongTermTicket(startDate, ticketType, discount, oneTimeDiscount, id);
	}
	
	public SingleFairTicket createSingleFairTicket() {
		
		return null;
	}
}
