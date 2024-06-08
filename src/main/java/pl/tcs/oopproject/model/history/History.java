package pl.tcs.oopproject.model.history;

import pl.tcs.oopproject.model.ticket.LongTermTrainTicket;
import pl.tcs.oopproject.model.ticket.SingleJourneyTrainTicket;
import pl.tcs.oopproject.model.ticket.TrainTicket;

import java.sql.SQLException;
import java.util.ArrayList;

public class History {
	private  ArrayList<SingleJourneyTrainTicket> singleJourneyTickets;
	private ArrayList<LongTermTrainTicket> longTermTickets;
	
	private void setData() {
		//SET DATA ABOUT TICKETS
		//
	}
	
	public ArrayList<LongTermTrainTicket> activeLongTermTickets(){
		if(longTermTickets.isEmpty()) setData();
		ArrayList<LongTermTrainTicket> activeTickets = new ArrayList<>();
		for(LongTermTrainTicket t : longTermTickets) {
			if(t.isActive())
				activeTickets.add(t);
		}
		return activeTickets;
	}
	
	public ArrayList<SingleJourneyTrainTicket> activeSingleJourneyTickets(){
		if(singleJourneyTickets.isEmpty()) setData();
		ArrayList<SingleJourneyTrainTicket> activeTickets = new ArrayList<>();
		for(SingleJourneyTrainTicket t : singleJourneyTickets) {
			if(t.isActive())
				activeTickets.add(t);
		}
		return activeTickets;
	}
	
	public ArrayList<SingleJourneyTrainTicket> archivedSingleJourneyTickets() {
		if(singleJourneyTickets.isEmpty()) setData();
		ArrayList<SingleJourneyTrainTicket> archivedTickets = new ArrayList<>();
		for(SingleJourneyTrainTicket t : singleJourneyTickets) {
			if(!t.isActive())
				archivedTickets.add(t);
		}
		return archivedTickets;
	}
	
	public ArrayList<LongTermTrainTicket> archivedLongTermTickets() {
		if(longTermTickets.isEmpty()) setData();
		ArrayList<LongTermTrainTicket> archivedTickets = new ArrayList<>();
		for(LongTermTrainTicket t : longTermTickets) {
			if(!t.isActive())
				archivedTickets.add(t);
		}
		return archivedTickets;
	}
}
