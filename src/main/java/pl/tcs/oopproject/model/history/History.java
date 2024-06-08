package pl.tcs.oopproject.model.history;

import pl.tcs.oopproject.model.ticket.LongTermTrainTicket;
import pl.tcs.oopproject.model.ticket.SingleJourneyTrainTicket;
import pl.tcs.oopproject.model.ticket.TrainTicket;

import java.util.ArrayList;

public class History {
	private  ArrayList<SingleJourneyTrainTicket> singleJourneyTickets;
	private ArrayList<LongTermTrainTicket> longTermTickets;
	
	private void setData() {
		//SET DATA ABOUT TICKETS
		//
	}
	
	public ArrayList<LongTermTrainTicket> activeLongTermTickets(){
		ArrayList<LongTermTrainTicket> activeTickets = new ArrayList<>();
		for(LongTermTrainTicket t : longTermTickets) {
			if(t.isActive())
				activeTickets.add(t);
		}
		return activeTickets;
	}
	
	public ArrayList<SingleJourneyTrainTicket> activeSingleJourneyTickets(){
		ArrayList<SingleJourneyTrainTicket> activeTickets = new ArrayList<>();
		for(SingleJourneyTrainTicket t : singleJourneyTickets) {
			if(t.isActive())
				activeTickets.add(t);
		}
		return activeTickets;
	}
	
	public ArrayList<SingleJourneyTrainTicket> archivedSingleJourneyTickets() {
		ArrayList<SingleJourneyTrainTicket> archivedTickets = new ArrayList<>();
		for(SingleJourneyTrainTicket t : singleJourneyTickets) {
			if(!t.isActive())
				archivedTickets.add(t);
		}
		return archivedTickets;
	}
	
	public ArrayList<LongTermTrainTicket> archivedLongTermTickets() {
		ArrayList<LongTermTrainTicket> archivedTickets = new ArrayList<>();
		for(LongTermTrainTicket t : longTermTickets) {
			if(!t.isActive())
				archivedTickets.add(t);
		}
		return archivedTickets;
	}
}
