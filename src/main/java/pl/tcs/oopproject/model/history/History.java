package pl.tcs.oopproject.model.history;

import pl.tcs.oopproject.model.ticket.LongTermTrainTicket;
import pl.tcs.oopproject.model.ticket.SingleJourneyTrainTicket;
import pl.tcs.oopproject.model.ticket.TrainTicket;

import java.sql.SQLException;
import java.util.ArrayList;

public class History {
	private  ArrayList<HistorySingleJourneyTicket> singleJourneyTickets;
	private ArrayList<HistoryLongTermTicket> longTermTickets;
	
	private void setData() {
		//SET DATA ABOUT TICKETS
		//
	}
	
	public ArrayList<HistoryLongTermTicket> activeLongTermTickets(){
		if(longTermTickets.isEmpty()) setData();
		ArrayList<HistoryLongTermTicket> activeTickets = new ArrayList<>();
		for(HistoryLongTermTicket t : longTermTickets) {
			if(t.isActive())
				activeTickets.add(t);
		}
		return activeTickets;
	}
	
	public ArrayList<HistorySingleJourneyTicket> activeSingleJourneyTickets(){
		if(singleJourneyTickets.isEmpty()) setData();
		ArrayList<HistorySingleJourneyTicket> activeTickets = new ArrayList<>();
		for(HistorySingleJourneyTicket t : singleJourneyTickets) {
			if(t.isActive())
				activeTickets.add(t);
		}
		return activeTickets;
	}
	
	public ArrayList<HistorySingleJourneyTicket> archivedSingleJourneyTickets() {
		if(singleJourneyTickets.isEmpty()) setData();
		ArrayList<HistorySingleJourneyTicket> archivedTickets = new ArrayList<>();
		for(HistorySingleJourneyTicket t : singleJourneyTickets) {
			if(!t.isActive())
				archivedTickets.add(t);
		}
		return archivedTickets;
	}
	
	public ArrayList<HistoryLongTermTicket> archivedLongTermTickets() {
		if(longTermTickets.isEmpty()) setData();
		ArrayList<HistoryLongTermTicket> archivedTickets = new ArrayList<>();
		for(HistoryLongTermTicket t : longTermTickets) {
			if(!t.isActive())
				archivedTickets.add(t);
		}
		return archivedTickets;
	}
}
