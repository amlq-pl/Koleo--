package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.model.ticket.TrainTicket;

import java.util.ArrayList;

public interface TicketGetter {
	/**
	returns list of all tickets bought by the user login
	 */
	public ArrayList<TrainTicket> getTickets(String login);
	
}
