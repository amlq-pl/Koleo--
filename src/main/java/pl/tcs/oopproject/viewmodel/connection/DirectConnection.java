package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.Station;

import java.util.Iterator;
import java.util.List;

public class DirectConnection implements ConnectionInterface { //some kind of decorator to TrainConnection
	private final String company;
	private final TrainConnection connection;
	private final int number; //number of a train
	private final double cost;
	private final TrainType trainType;
	
	public DirectConnection(String company, int number, double cost, TrainType trainType, TrainConnection connection) {
		this.company = company;
		this.connection = connection;
		this.number = number;
		this.trainType = trainType;
		this.cost = cost;
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getCompany() {
		return company;
	}
	
	public TrainType getTrainType() {
		return trainType;
	}
	
	@Override
	public Station getFirstStation() {
		return connection.getFirstStation();
	}
	
	@Override
	public Station getLastStation() {
		return connection.getLastStation();
	}
	
	@Override
	public Station getStationAt(int index) throws IndexOutOfBoundsException {
		return connection.getStationAt(index);
	}
	
	@Override
	public int getIndexOfStation(String town) throws IllegalArgumentException {
		return connection.getIndexOfStation(town);
	}
	
	@Override
	public Iterator<Station> getIterator() {
		return connection.getIterator();
	}
	
	@Override
	public List<Station> getStations() {
		return connection.getStations();
	}
	
	public double getCost() {
		return cost;
	}
	
	@Override
	public int getSize() {
		return connection.getSize();
	}
	
	public boolean contains(String town) {
		return connection.contains(town);
	}
	
	public Station getStation(String station) {
		return connection.getStation(station);
	}
	
}
