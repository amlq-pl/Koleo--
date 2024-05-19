package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.Station;
import pl.tcs.oopproject.viewmodel.station.StationInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConnectionWithTransfers implements RailwayInterface, ConnectionInterface, Comparable<ConnectionWithTransfers> {
	Station stationA;
	Station stationB;
	List<DirectConnection> trains; //trains that general connection consists of
	
	ConnectionWithTransfers(Station A, Station B, List<DirectConnection> directConnections) {
		this.trains = directConnections;
		this.stationA = A;
		this.stationB = B;
	}
	
	public List<DirectConnection> getTrains() {
		return trains;
	}
	
	@Override
	public TrainType getTrainType() {
		if(trains.size() != 1)
			return null;
		else
			return trains.get(0).getTrainType();
	}
	
	@Override
	public StationInterface getFirstStation() {
		return stationA;
	}
	
	@Override
	public StationInterface getLastStation() {
		return stationB;
	}
	
	@Override
	public StationInterface getStationAt(int index) throws IndexOutOfBoundsException {
		//CODE HERE
		return null;
	}
	//Trains store direct connections, but as a whole, so eq A - B - C - D - E,
	//when actually needed is B - E
	
	@Override
	public int IndexOfStation(String town) throws IllegalArgumentException {
		//CODE HERE
		return 0;
	}
	//Trains store direct connections, but as a whole, so eq A - B - C - D - E,
	//when actually needed is B - E
	
	@Override
	public Iterator<StationInterface> getIterator() {
		//CODE HERE
		return null;
	}
	
	@Override
	public List<StationInterface> getStations() {
		return null;
	}
	
	@Override
	public int getNumberOfTransfers() {
		return trains.size();
	}
	
	@Override
	public LocalDateTime getDepartureTime() {
		return stationA.getDepartureTime();
	}
	
	@Override
	public LocalDateTime getArrivalTime() {
		return stationB.getArrivalTime();
	}
	
	public List<String> getCompanies() {
		List<String> list = new ArrayList<>();
		for(DirectConnection t : trains)
			list.add(t.getCompany());
		return list;
	}
	
	@Override
	public List<StationInterface> getTransferStations() {
		//CODE HERE
		return null;
	}
	
	
	@Override
	public int getCost() {
		//CODE HERE
		return 0;
	}
	
	@Override
	public int getSize() {
		return 0;
	}
	
	@Override
	
	public int compareTo(ConnectionWithTransfers o) {
		return getDepartureTime().compareTo(((ConnectionWithTransfers) o).getDepartureTime());
	}
}
