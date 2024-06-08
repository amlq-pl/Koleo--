package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.model.ticket.LongTermTrainTicket;
import pl.tcs.oopproject.model.ticket.SingleJourneyTrainTicket;
import pl.tcs.oopproject.model.ticket.TrainTicket;

import java.util.ArrayList;

public interface TicketGetter {
	/**
	returns list of all tickets bought by the user login
	 */
	ArrayList<SingleJourneyTrainTicket> getSingleUseTickets(String login);
	
	ArrayList<LongTermTrainTicket> getLongTermTickets(String login);
	
}
