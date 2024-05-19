package pl.tcs.oopproject.viewmodel.findconnection;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Connection {
	//list of direct connections the main connection consists of
	List<DirectConnection> trail;
	//list of stations at which transfer is expected, including first and last
	List<Integer> transferStations;
	
	public int getNumberOfTransfers() {
		return trail.size() - 1;
	}
	
	public LocalDateTime getDepartureTime() {
		return trail.getFirst().getFirstStation().getDepartureDate();
	}
	
	public LocalDateTime getArrivalTime() {
		return  trail.getLast().getStationAt(transferStations.getLast()).getArrivalDate();
	}

	public List<String> getCompanies() {
		List<String> companies = new ArrayList<>();
		for(DirectConnection dc : trail) companies.add(dc.getCompany());
		return companies;
	}
	
	public List<Station> getTransferStation() {
		List<Station> transfers = new ArrayList<>();
		for (int i = 0; i < transferStations.size() - 1; ++i)
			transfers.add(trail.get(i).getStationAt(transferStations.get(i)));
		return transfers;
	}
	
	public abstract int cost();
}
