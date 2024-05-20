package pl.tcs.oopproject.viewmodel.station;

import pl.tcs.oopproject.viewmodel.connection.DirectConnection;

import java.time.LocalDateTime;

public interface StationInterface {
	String getTown();
	LocalDateTime getArrivalTime();
	LocalDateTime getDepartureTime();
	
	DirectConnection getTrain();
	
	void display();
}
