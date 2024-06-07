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
import java.util.List;

public class TicketFactory {
	
	public ArrayList<LongTermTrainTicket> createLongTermTicket(ArrayList<LongTermTicketType> tickets, Discount discount, Voucher voucher, LocalDate startDate) {
		return null;
	}
	
	public ArrayList<LongTermTrainTicket> createLongTermTicket(ArrayList<LongTermTicketType> tickets, Discount discount, Voucher voucher, LocalDate startDate, Person person) {
		return null;
	}
	
	public ArrayList<SingleJourneyTrainTicket> createSingleFairTicket(Discount discount, Voucher voucher, Details details, ArrayList<TrainsAssignedSeats> seats) {
		return null;
	}
	
	public ArrayList<SingleJourneyTrainTicket> createSingleFairTicket(Discount discount, Voucher voucher, Details details, ArrayList<TrainsAssignedSeats> seats, Person person) {
		return null;
	}
	
	public void refund(TrainTicket ticket) {
		if (ticket.returned()) throw new AlreadyReturnedTicketException();
		//RETURN TICKET - SAVE IT IN DATABASE
		ticket.returnTicket();
	}
}
