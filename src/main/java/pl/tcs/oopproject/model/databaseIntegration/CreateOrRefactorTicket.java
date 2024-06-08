package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.model.assignedSeat.TrainsAssignedSeats;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.ticket.Details;
import pl.tcs.oopproject.model.ticket.LongTermTicketType;
import pl.tcs.oopproject.model.ticket.LongTermTrainTicket;
import pl.tcs.oopproject.model.ticket.SingleJourneyTrainTicket;
import pl.tcs.oopproject.model.users.Person;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface CreateOrRefactorTicket {
	// RETURN ID OF THE TICKET
	ArrayList<LongTermTrainTicket> saveLongTermTicket(ArrayList<LocalDate> startDate, ArrayList<Discount> discount, ArrayList<Voucher> voucher, String login, ArrayList<LongTermTicketType> ticketType, ArrayList<Person> person) throws SQLException;
	
	/**
	 * the method has to return ArrayList of SingleUseTickets (Person, voucher, discount, details, ArrayList<TrainsAssignedSeats>  (each seat has getTrain)
	 */
	ArrayList<SingleJourneyTrainTicket> saveSingleJourneyTicket(ArrayList<Person> person, ArrayList<Discount> discount, ArrayList<Voucher> voucher, ArrayList<Details> details, ArrayList<TrainsAssignedSeats> seats, String login) throws SQLException;
	
	boolean returnSingleJourneyTrainTicket(int id);
	
	boolean returnLongTermTrainTicket(int id);
}
