package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.Station;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConnectionFinder implements FindConnectionInterface{
	ArrayList<DirectConnection> trains;
	Station stationA;
	Station stationB;
	LocalDateTime departureDate;
	LocalDateTime arrivalDate;
	
	ConnectionFinder(Station stationA, Station stationB, LocalDateTime departureDate, LocalDateTime arrivalDate) {
		this.stationA = stationA;
		this.stationB = stationB;
		this.arrivalDate = arrivalDate;
		this.departureDate = departureDate;
	}
	
	private void setTrains() {
		//TO DO : get information from the database and save them
	}
	
	@Override
	public List<ConnectionWithTransfers> getRoutes() {
		if(trains == null) setTrains();
		return null;
	}
	
	@Override
	public List<ConnectionWithTransfers> getCheapRoutes() {
		if(trains == null) setTrains();
		return null;
	}
	
	@Override
	public List<ConnectionWithTransfers> getRoutesWithoutTransfers() {
		if(trains == null) setTrains();
		return null;
	}
}
