package pl.tcs.oopproject.viewmodel.tickets;

import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.exception.AlreadyReturnedTicketException;
import pl.tcs.oopproject.model.assignedSeat.TrainsAssignedSeats;
import pl.tcs.oopproject.model.ticket.*;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.time.LocalDate;
import java.util.ArrayList;

public class TicketFactory {
	
	public LongTermTrainTicket createLongTermTicket(LongTermTicketType ticketType, Discount discount, Voucher voucher, LocalDate startDate) {
		int id = 0; // = SAVE DATA IN DATABASE, FUNCTION MUST RETURN ID OF THE ORDER
		return new LongTermTrainTicket(startDate, ticketType, discount, voucher, id, ActiveUser.getPerson());
	}
	
	public LongTermTrainTicket createLongTermTicket(LongTermTicketType ticketType, Discount discount, Voucher voucher, LocalDate startDate, Person person) {
		int id = 0; // = SAVE DATA IN DATABASE, FUNCTION MUST RETURN ID OF THE ORDER
		return new LongTermTrainTicket(startDate, ticketType, discount, voucher, id, person);
	}
	
	public ArrayList<SingleJourneyTrainTicket> createSingleFairTicket(Discount discount, Voucher voucher, Details details, ArrayList<TrainsAssignedSeats> seats) {
		int id = 0; //=SAVE IN DATABASE, FUNCTION TAKES PLACE (FROM USER), SAVE DATA IN DATABASE AND MUST RETURN Lit of tickets
		return null;
	}
	
	public SingleJourneyTrainTicket createSingleFairTicket(Discount discount, Voucher voucher, Details details, ArrayList<TrainsAssignedSeats> seats, Person person) {
		int id = 0; //=SAVE IN DATABASE, FUNCTION TAKES PLACE (FROM USER), SAVE DATA IN DATABASE AND MUST RETURN ID OF THE ORDER
		return null;
	}
	
	public void refund(TrainTicket ticket) {
		if (ticket.returned()) throw new AlreadyReturnedTicketException();
		//RETURN TICKET - SAVE IT IN DATABASE
		ticket.returnTicket();
	}
}
