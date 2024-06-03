package pl.tcs.oopproject.model.connection;

import pl.tcs.oopproject.model.discount.PriceInterface;
import pl.tcs.oopproject.model.station.Station;

import java.time.LocalDateTime;
import java.util.List;

public interface TransferableRoute {
	
	int numberOfTransfers();
	
	LocalDateTime departureTime();
	
	LocalDateTime arrivalTime();
	
	List<String> companies();
	
	List<Station> transferStations();
	
	PriceInterface cost();
	
}
