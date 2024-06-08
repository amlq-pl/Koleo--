package pl.tcs.oopproject.viewmodel.tickets;

import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.exception.AlreadyReturnedTicketException;
import pl.tcs.oopproject.model.assignedSeat.TrainsAssignedSeats;
import pl.tcs.oopproject.model.ticket.*;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.postgresDatabaseIntegration.CreateOrRefactor;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TicketFactory {
	
	public ArrayList<LongTermTrainTicket> createLongTermTicket(ArrayList<LongTermTicketType> tickets, ArrayList<Discount> discount, ArrayList<Voucher> voucher, ArrayList<LocalDate> startDate, ArrayList<Person> person) throws SQLException {
		CreateOrRefactor creator = new CreateOrRefactor();
		return creator.saveLongTermTicket(startDate, discount, voucher, ActiveUser.getActiveUser(), tickets, person);
	}
	
	public ArrayList<SingleJourneyTrainTicket> createSingleJourneyTicket(ArrayList<Discount> discount, ArrayList<Voucher> voucher, ArrayList<Details> details, ArrayList<TrainsAssignedSeats> seats, ArrayList<Person> person) throws SQLException {
		CreateOrRefactor creator = new CreateOrRefactor();
		return creator.saveSingleJourneyTicket(person, discount, voucher, details, seats, ActiveUser.getActiveUser());
	}
	
	public void refund(TrainTicket ticket) {
		if (ticket.returned()) throw new AlreadyReturnedTicketException();
		CreateOrRefactor refactor = new CreateOrRefactor();
		//refactor.returnTicket(ticket.id());
		ticket.returnTicket();
	}
}
