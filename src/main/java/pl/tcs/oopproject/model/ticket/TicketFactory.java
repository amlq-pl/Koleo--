package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.exception.AlreadyReturnedTicketException;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.time.LocalDate;

public class TicketFactory {
	
	public LongTermTrainTicket createLongTermTicket(LongTermTicketType ticketType, Discount discount, Voucher voucher, LocalDate startDate) {
		int id = 0; // = SAVE DATA IN DATABASE, FUNCTION MUST RETURN ID OF THE ORDER
		return new LongTermTrainTicket(startDate, ticketType, discount, voucher, id);
	}
	
	public SingleJourneyTrainTicket createSingleFairTicket(Discount discount, Voucher voucher, Details details) {
		int id = 0; //=SAVE IN DATABASE, FUNCTION TAKES PLACE (FROM USER), SAVE DATA IN DATABASE AND MUST RETURN ID OF THE ORDER
		return new SingleJourneyTrainTicket(ActiveUser.getPlace(), discount, voucher, id, details);
	}
	
	public void refund(TrainTicket ticket) {
		if (ticket.returned()) throw new AlreadyReturnedTicketException();
		//RETURN TICKET - SAVE IT IN DATABASE
		ticket.returnTicket();
	}
	
}
