package pl.tcs.oopproject.viewmodel.station;

import pl.tcs.oopproject.viewmodel.exception.IllegalOperationException;
import pl.tcs.oopproject.viewmodel.connection.DirectConnection;

import java.time.LocalDateTime;

public class Station{
	private final String town;
	private final LocalDateTime departureTime;
	private final LocalDateTime arrivalTime;
	private DirectConnection directConnection; //which departs and arrives at given time
	
	public Station(String town, LocalDateTime departureTime, LocalDateTime arrivalDate) {
		this.town = town;
		this.arrivalTime = arrivalDate;
		this.departureTime = departureTime;
		directConnection = null;
	}
	
	public void setTrain(DirectConnection directConnection) {
		if(this.directConnection == null) throw new IllegalOperationException();
		this.directConnection = directConnection;
	}
	
	public String getTown() {
		return town;
	}
	
	public LocalDateTime getArrivalTime() {
		return arrivalTime;
	}
	
	public LocalDateTime getDepartureTime() {
		return departureTime;
	}
	
	public DirectConnection getTrain() {
		return this.directConnection;
	}
	
	public void display() {
		System.out.println("station: " + town + " arrives at " + departureTime + " departs at " + arrivalTime);
		
	}
}
