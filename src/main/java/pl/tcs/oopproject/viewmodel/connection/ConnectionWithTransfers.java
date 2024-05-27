package pl.tcs.oopproject.viewmodel.connection;

import org.jetbrains.annotations.NotNull;
import pl.tcs.oopproject.viewmodel.discount.PricePLN;
import pl.tcs.oopproject.viewmodel.exception.NoRouteFoundException;
import pl.tcs.oopproject.viewmodel.station.Station;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConnectionWithTransfers implements ConnectionInterface, ConnectionWithTransfersInterface, Comparable<ConnectionWithTransfers> {
	private final Station stationA;
	private final Station stationB;
	private final ArrayList<DirectConnection> trains; //trains that general connection consists of
	private final ArrayList<String> transferStations; //stations when customer should get into
	
	public ConnectionWithTransfers(Station A, Station B, ArrayList<DirectConnection> directConnections, ArrayList<String> transferStations) {
		this.trains = directConnections;
		this.stationA = A;
		this.stationB = B;
		this.transferStations = transferStations;
	}
	
	public List<DirectConnection> getTrains() {
		return trains;
	}
	
	public TrainIsReservation getTrainType() {
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
		ArrayList<Station> list = new ArrayList<>();
		
		int size = trains.size();
		
		for(int i = 0; i < size - 1; ++i) {
			int j = trains.get(i).getIndexOfStation(transferStations.get(i));
			int k = trains.get(i).getIndexOfStation(transferStations.get(i + 1));
			for(int t = j; t < k; ++t)
				list.add(trains.get(i).getStationAt(t));
		}
		
		int j = trains.get(size - 1).getIndexOfStation(transferStations.get(size - 1));
		int k = trains.get(size - 1).getIndexOfStation(stationB.getTown());
		for(int t = j; t <= k; ++t)
			list.add(trains.get(size - 1).getStationAt(t));
		
		return list;
	}
	
	@Override
	public int getNumberOfTransfers() {
		return trains.size() - 1;
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
	public PricePLN getCost() {
		double cost = 0;
		
		int size = trains.size();
		
		transferStations.add(stationB.getTown());
		
		for(int i = 0; i < size; ++i) {
			double cost1 = trains.get(i).getCost().getPriceValue();
			int j = trains.get(i).getIndexOfStation(transferStations.get(i));
			int k = trains.get(i).getIndexOfStation(transferStations.get(i + 1));
			cost += cost1 * (k - j + 1) / trains.get(i).getSize();
		}
		
		transferStations.remove(stationB.getTown());
		return new PricePLN(cost);
	}
	
	@Override
	public int getSize() {
		return getStations().size();
	}
	
	@Override
	public int compareTo(@NotNull ConnectionWithTransfers o) {
		return o.getDepartureTime().compareTo(getDepartureTime());
	}
	
	public void displayLess() {
		System.out.println("Departure Station: " + getFirstStation().getTown() + " " + getFirstStation().getDepartureTime());
		System.out.println("Arrival Station: " + getLastStation().getTown() + " " + getLastStation().getArrivalTime());
		System.out.println("Cost: " + getCost().toString());
	}
	
	
	public void display() {
		for(DirectConnection d : trains)
			d.display();
	}
}
