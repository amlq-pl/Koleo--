package pl.tcs.oopproject.model.ticket;

import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.exception.AlreadyReturnedTicketException;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.time.LocalDate;

public class TicketFactory {
	
	public LongTermTrainTicket createLongTermTicket(LongTermTicketType ticketType, Discount discount, Voucher voucher, LocalDate startDate) {
		int id = 0; // = SAVE DATA IN DATABASE, FUNCTION MUST RETURN ID OF THE ORDER
		return new LongTermTrainTicket(startDate, ticketType, discount, voucher, id, ActiveUser.getPerson());
	}
	
	public LongTermTrainTicket createLongTermTicket(LongTermTicketType ticketType, Discount discount, Voucher voucher, LocalDate startDate, Person person) {
		int id = 0; // = SAVE DATA IN DATABASE, FUNCTION MUST RETURN ID OF THE ORDER
		return new LongTermTrainTicket(startDate, ticketType, discount, voucher, id, person);
	}
	
	public SingleJourneyTrainTicket createSingleFairTicket(Discount discount, Voucher voucher, Details details, MultiStopRoute train) {
		int id = 0; //=SAVE IN DATABASE, FUNCTION TAKES PLACE (FROM USER), SAVE DATA IN DATABASE AND MUST RETURN ID OF THE ORDER
		return new SingleJourneyTrainTicket(ActiveUser.getPlace(), discount, voucher, id, details, train, ActiveUser.getPerson());
	}
	
	public SingleJourneyTrainTicket createSingleFairTicket(Discount discount, Voucher voucher, Details details, MultiStopRoute train, Person person) {
		int id = 0; //=SAVE IN DATABASE, FUNCTION TAKES PLACE (FROM USER), SAVE DATA IN DATABASE AND MUST RETURN ID OF THE ORDER
		return new SingleJourneyTrainTicket(ActiveUser.getPlace(), discount, voucher, id, details, train, person);
	}
	
	public void refund(TrainTicket ticket) {
		if (ticket.returned()) throw new AlreadyReturnedTicketException();
		//RETURN TICKET - SAVE IT IN DATABASE
		ticket.returnTicket();
	}
}
