package pl.tcs.oopproject.model.place;

import pl.tcs.oopproject.model.connection.ConnectionWithTransfers;

import java.util.List;

public class Place {
	private ConnectionWithTransfers connection;
	private List<SpecificSeat> seats;
	
	public Place(ConnectionWithTransfers connection, List<SpecificSeat> seats) {
		this.connection = connection;
		this.seats = seats;
	}
	
	public List<SpecificSeat> getSeat() {
		return seats;
	}
	
	public void setSeat(List<SpecificSeat> seats) {
		this.seats = seats;
	}
	
	public ConnectionWithTransfers getConnection() {
		return connection;
	}
	
	public void setConnection(ConnectionWithTransfers connection) {
		this.connection = connection;
	}
	
}
