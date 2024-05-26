package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.exception.NoRouteFoundException;
import pl.tcs.oopproject.viewmodel.station.Station;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConnectionWithTransfers implements ConnectionInterface, ConnectionWithTransfersInterface, Comparable<ConnectionWithTransfers> {
	private final Station stationA;
	private final Station stationB;
	private final List<DirectConnection> trains; //trains that general connection consists of
	private final ArrayList<String> transferStations; //stations when customer should get into
	
	public ConnectionWithTransfers(Station A, Station B, List<DirectConnection> directConnections, ArrayList<String> transferStations) {
		this.trains = directConnections;
		this.stationA = A;
		this.stationB = B;
		this.transferStations = transferStations;
	}
	
	public List<DirectConnection> getTrains() {
		return trains;
	}
	
	public TrainType getTrainType() {
		if(trains.size() != 1)
			return null;
		else
			return trains.get(0).getTrainType();
	}
	
	@Override
	public Station getFirstStation() {
		return stationA;
	}
	
	@Override
	public Station getLastStation() {
		return stationB;
	}
	
	@Override
	public Station getStationAt(int index) throws IndexOutOfBoundsException {
		if (trains.isEmpty()) throw new NoRouteFoundException();
		int maxSize;
		int c;
		for (int i = 0; i < trains.size(); ++i) {
			maxSize = trains.get(i).getSize() - trains.get(i).getIndexOfStation(transferStations.get(i));
			c = trains.get(i).getIndexOfStation(transferStations.get(i));
			if (maxSize <= index) return trains.get(i).getStationAt(c + index);
			index -= maxSize;
		} //TO CHECK IT!!!


		throw new IndexOutOfBoundsException();
	}
	
	@Override
	public int getIndexOfStation(String town) throws IllegalArgumentException {
		//CODE HERE
		return 0;
	}
	//Trains store direct connections, but as a whole, so eq A - B - C - D - E,
	//when actually needed is B - E
	
	@Override
	public Iterator<Station> getIterator() {
		//CODE HERE
		return null;
	}
	
	@Override
	public List<Station> getStations() {
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
	public List<Station> getTransferStations() {
		if(transferStations.isEmpty()) throw new NoRouteFoundException();
		List<Station> stations = new ArrayList<>();

		for(int i = 1; i < transferStations.size(); ++i) {
			int index = trains.get(i).getIndexOfStation(transferStations.get(i));
			stations.add(trains.get(i).getStationAt(index));
		}
		return stations;
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
		return ((ConnectionWithTransfers) o).getDepartureTime().compareTo(getDepartureTime());
	}
}
