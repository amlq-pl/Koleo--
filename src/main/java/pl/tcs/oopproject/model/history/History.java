package pl.tcs.oopproject.model.history;

import pl.tcs.oopproject.postgresDatabaseIntegration.CreateOrRefactor;
import pl.tcs.oopproject.postgresDatabaseIntegration.TicketGet;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.sql.SQLException;
import java.util.ArrayList;

public class History {
	private  ArrayList<HistoryLongTermTicket> activeLongTermTickets;
	private ArrayList<HistoryLongTermTicket> archivedLongTermTickets;
	private ArrayList<HistorySingleJourneyTicket> activeSingleJourneyTickets;
	private ArrayList<HistorySingleJourneyTicket> archivedSingleJourneyTickets;
	
	private void setData() throws SQLException {
		TicketGet ticketGet = new TicketGet();
		ArrayList<HistorySingleJourneyTicket> singleJourneyTickets = ticketGet.getSingleUseTickets(ActiveUser.getActiveUser());
		ArrayList<HistoryLongTermTicket> longTermTickets = ticketGet.getLongTermTickets(ActiveUser.getActiveUser());
		
		activeLongTermTickets = new ArrayList<>();
		for(HistoryLongTermTicket t : longTermTickets) {
			if(t.isActive())
				activeLongTermTickets.add(t);
		}
		
		archivedLongTermTickets = new ArrayList<>();
		for(HistoryLongTermTicket t : longTermTickets) {
			if(!t.isActive())
				archivedLongTermTickets.add(t);
		}
		
		activeSingleJourneyTickets = new ArrayList<>();
		for(HistorySingleJourneyTicket t : singleJourneyTickets) {
			if(t.isActive())
				activeSingleJourneyTickets.add(t);
		}
		
		archivedSingleJourneyTickets = new ArrayList<>();
		for(HistorySingleJourneyTicket t : singleJourneyTickets) {
			if(!t.isActive())
				archivedSingleJourneyTickets.add(t);
		}
		
		
		
		
		
	}
	
	public ArrayList<HistoryLongTermTicket> activeLongTermTickets() throws SQLException {
		if(activeLongTermTickets == null) setData();
		return activeLongTermTickets;
	}
	
	public ArrayList<HistorySingleJourneyTicket> activeSingleJourneyTickets() throws SQLException {
		if(activeSingleJourneyTickets == null) setData();
		return activeSingleJourneyTickets;
	}
	
	public ArrayList<HistorySingleJourneyTicket> archivedSingleJourneyTickets() throws SQLException {
		if(archivedSingleJourneyTickets == null) setData();
		return archivedSingleJourneyTickets;
	}
	
	public ArrayList<HistoryLongTermTicket> archivedLongTermTickets() throws SQLException {
		if(archivedLongTermTickets == null) setData();
		return archivedLongTermTickets;
	}
	public boolean refundLongTermTicket(HistoryLongTermTicket ticket) throws SQLException {
		if (ticket.refunded()) return false;
		CreateOrRefactor refactor = new CreateOrRefactor();
		refactor.returnLongTermTrainTicket(ticket.id());
	
		for(HistoryLongTermTicket t : activeLongTermTickets) {
			if(t.id() == ticket.id()) {
				ticket.refundTicket();
				archivedLongTermTickets.add(t);
				activeLongTermTickets.remove(t);
			}
		}
		return true;
	}
	
	public boolean refundSingleUSeTicket(HistorySingleJourneyTicket ticket) throws SQLException {
		if (ticket.refunded()) return false;
		CreateOrRefactor refactor = new CreateOrRefactor();
		refactor.returnSingleJourneyTrainTicket(ticket.id());
		
		for(HistorySingleJourneyTicket t : activeSingleJourneyTickets) {
			if(t.id() == ticket.id()) {
				ticket.refundTicket();
				archivedSingleJourneyTickets.add(t);
				activeSingleJourneyTickets.remove(t);
			}
		}
		return true;
	}
	
}
