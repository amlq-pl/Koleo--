package pl.tcs.oopproject.viewmodel.place;

import pl.tcs.oopproject.viewmodel.connection.ConnectionWithTransfers;
import java.util.List;

public class Place {
	private ConnectionWithTransfers connection;
	private List<SpecificSeat> seats;
	
	Place(ConnectionWithTransfers connection, List<SpecificSeat> seats) {
		this.connection = connection;
		this.seats = seats;
	}
	
	public void setConnection(ConnectionWithTransfers connection) {
		this.connection = connection;
	}
	
	public void setSeat(List<SpecificSeat> seats) {
		this.seats = seats;
	}
	
	public List<SpecificSeat> getSeat() {
		return seats;
	}
	
	public ConnectionWithTransfers getConnection() {
		return connection;
	}
	
}
