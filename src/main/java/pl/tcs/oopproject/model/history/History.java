package pl.tcs.oopproject.model.history;

import pl.tcs.oopproject.model.ticket.TrainTicket;

import java.util.ArrayList;

public class History {
	private static ArrayList<TrainTicket> tickets;
	
	void setData() {
		//SET DATA ABOUT TICKETS
	}
	
	private ArrayList<TrainTicket> activeTickets(){
		ArrayList<TrainTicket> activeTickets = new ArrayList<>();
		for(TrainTicket t : tickets) {
			if(t.isActive())
				activeTickets.add(t);
		}
		return activeTickets;
	}
	
	private ArrayList<TrainTicket> archivedTickets() {
		ArrayList<TrainTicket> archivedTickets = new ArrayList<>();
		for(TrainTicket t : tickets) {
			if(!t.isActive())
				archivedTickets.add(t);
		}
		return archivedTickets;
	}
}
