package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.Station;

import java.time.LocalDateTime;
import java.util.List;

public interface ConnectionWithTransfersInterface {

	int getNumberOfTransfers();
	
	LocalDateTime getDepartureTime();

	LocalDateTime getArrivalTime();

	List<String> getCompanies();
	
	List<Station> getTransferStations();
	
	int getCost();
	
}
