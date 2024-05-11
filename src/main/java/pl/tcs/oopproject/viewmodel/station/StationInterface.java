package pl.tcs.oopproject.viewmodel.station;

import pl.tcs.oopproject.viewmodel.connection.DirectConnection;

import java.time.LocalDateTime;

public interface StationInterface {
	public String getTown();
	public LocalDateTime getArrivalTime();
	public LocalDateTime getDepartureTime();
	
	public DirectConnection getTrain();
	
	public void display();
}
