package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.StationInterface;

import java.time.LocalDateTime;
import java.util.List;

public interface ConnectionInterface {

	int getNumberOfTransfers();
	
	LocalDateTime getDepartureTime();
	LocalDateTime getArrivalTime();

	List<String> getCompanies();
	
	List<StationInterface> getTransferStations();
	
	int getCost();
	
}
