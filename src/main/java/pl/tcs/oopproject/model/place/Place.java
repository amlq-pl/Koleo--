package pl.tcs.oopproject.model.place;

import pl.tcs.oopproject.model.connection.MultiStopRoute;

import java.util.List;

public class Place {
	private MultiStopRoute connection;
	private List<SpecificSeat> seats;
	
	public Place(MultiStopRoute connection, List<SpecificSeat> seats) {
		this.connection = connection;
		this.seats = seats;
	}
	
	public List<SpecificSeat> getSeat() {
		return seats;
	}
	
	public void setSeat(List<SpecificSeat> seats) {
		this.seats = seats;
	}
	
	public MultiStopRoute getConnection() {
		return connection;
	}
	
	public void setConnection(MultiStopRoute connection) {
		this.connection = connection;
	}
	
}
