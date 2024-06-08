package pl.tcs.oopproject.model.history;

import pl.tcs.oopproject.model.ticket.TrainTicket;

import java.util.ArrayList;

public class History {
	private static ArrayList<TrainTicket> tickets;
	
	private void setData() {
		//SET DATA ABOUT TICKETS
	}
	
	public ArrayList<TrainTicket> activeTickets(){
		ArrayList<TrainTicket> activeTickets = new ArrayList<>();
		for(TrainTicket t : tickets) {
			if(t.isActive())
				activeTickets.add(t);
		}
		return activeTickets;
	}
	
	public ArrayList<TrainTicket> archivedTickets() {
		ArrayList<TrainTicket> archivedTickets = new ArrayList<>();
		for(TrainTicket t : tickets) {
			if(!t.isActive())
				archivedTickets.add(t);
		}
		return archivedTickets;
	}
}
