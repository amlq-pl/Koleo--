package pl.tcs.oopproject.model.connection;

import pl.tcs.oopproject.model.discount.PriceInterface;
import pl.tcs.oopproject.model.station.Station;

import java.time.LocalDateTime;
import java.util.List;

public interface ConnectionWithTransfersInterface {
	
	int getNumberOfTransfers();
	
	LocalDateTime getDepartureTime();
	
	LocalDateTime getArrivalTime();
	
	List<String> getCompanies();
	
	List<Station> getTransferStations();
	
	PriceInterface getCost();
	
}
