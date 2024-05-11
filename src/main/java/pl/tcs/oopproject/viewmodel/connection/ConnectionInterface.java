package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.StationInterface;

import java.time.LocalDateTime;
import java.util.List;

public interface ConnectionInterface {

	public int getNumberOfTransfers();
	
	public LocalDateTime getDepartureTime();
	public LocalDateTime getArrivalTime();

	public List<String> getCompanies();
	
	public List<StationInterface> getTransferStations();
	
	public int cost();
	
}
