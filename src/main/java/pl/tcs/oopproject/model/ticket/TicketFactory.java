package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.OneTimeDiscount;
import pl.tcs.oopproject.model.exception.AlreadyReturnedTicketException;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.time.LocalDate;

public class TicketFactory {
	
	public LongTermTicket createLongTermTicket(LongTermTicketType ticketType, Discount discount, OneTimeDiscount oneTimeDiscount, LocalDate startDate) {
		int id = 0; // = SAVE DATA IN DATABASE, FUNCTION MUST RETURN ID OF THE ORDER
		return new LongTermTicket(startDate, ticketType, discount, oneTimeDiscount, id);
	}
	
	public SingleFareTicket createSingleFairTicket(Discount discount, OneTimeDiscount oneTimeDiscount, Details details) {
		int id = 0; //=SAVE IN DATABASE, FUNCTION TAKES PLACE (FROM USER), SAVE DATA IN DATABASE AND MUST RETURN ID OF THE ORDER
		return new SingleFareTicket(ActiveUser.getPlace(), discount, oneTimeDiscount, id, details);
	}
	
	public void refund(TicketInterface ticket) {
		if (ticket.returned()) throw new AlreadyReturnedTicketException();
		//RETURN TICKET - SAVE IT IN DATABASE
		ticket.returnTicket();
	}
	
}
