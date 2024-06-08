package pl.tcs.oopproject.model.history;

import pl.tcs.oopproject.postgresDatabaseIntegration.CreateOrRefactor;
import pl.tcs.oopproject.postgresDatabaseIntegration.TicketGet;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.sql.SQLException;
import java.util.ArrayList;

public class History {
	private  ArrayList<HistorySingleJourneyTicket> singleJourneyTickets;
	private ArrayList<HistoryLongTermTicket> longTermTickets;
	
	private void setData() throws SQLException {
		TicketGet ticketGet = new TicketGet();
		singleJourneyTickets = ticketGet.getSingleUseTickets(ActiveUser.getActiveUser());
		longTermTickets = ticketGet.getLongTermTickets(ActiveUser.getActiveUser());
	}
	
	public ArrayList<HistoryLongTermTicket> activeLongTermTickets() throws SQLException {
		if(longTermTickets.isEmpty()) setData();
		ArrayList<HistoryLongTermTicket> activeTickets = new ArrayList<>();
		for(HistoryLongTermTicket t : longTermTickets) {
			if(t.isActive() && !t.refunded())
				activeTickets.add(t);
		}
		return activeTickets;
	}
	
	public ArrayList<HistorySingleJourneyTicket> activeSingleJourneyTickets() throws SQLException {
		if(singleJourneyTickets.isEmpty()) setData();
		ArrayList<HistorySingleJourneyTicket> activeTickets = new ArrayList<>();
		for(HistorySingleJourneyTicket t : singleJourneyTickets) {
			if(t.isActive() || !t.refunded())
				activeTickets.add(t);
		}
		return activeTickets;
	}
	
	public ArrayList<HistorySingleJourneyTicket> archivedSingleJourneyTickets() throws SQLException {
		if(singleJourneyTickets.isEmpty()) setData();
		ArrayList<HistorySingleJourneyTicket> archivedTickets = new ArrayList<>();
		for(HistorySingleJourneyTicket t : singleJourneyTickets) {
			if(!t.isActive() || t.refunded())
				archivedTickets.add(t);
		}
		return archivedTickets;
	}
	
	public ArrayList<HistoryLongTermTicket> archivedLongTermTickets() throws SQLException {
		if(longTermTickets.isEmpty()) setData();
		ArrayList<HistoryLongTermTicket> archivedTickets = new ArrayList<>();
		for(HistoryLongTermTicket t : longTermTickets) {
			if(!t.isActive() || t.refunded())
				archivedTickets.add(t);
		}
		return archivedTickets;
	}
	
	public boolean refundLongTermTicket(HistoryLongTermTicket ticket) throws SQLException {
		CreateOrRefactor refactor = new CreateOrRefactor();
		if (ticket.refunded()) return false;
		refactor.returnLongTermTrainTicket(ticket.id());
		ticket.refundTicket();
		return true;
	}
}
