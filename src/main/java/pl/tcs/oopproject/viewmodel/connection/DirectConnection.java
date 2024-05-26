package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.Station;

import java.util.Iterator;
import java.util.List;

public class DirectConnection implements RailwayInterface { //some kind of decorator to Connection
	private final String company;
	private final Connection connection;
	private final int number; //number of a train
	private final double cost;
	private final TrainType trainType;
	
	DirectConnection(String company, int number, int cost, TrainType trainType, Connection connection) {
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
	
}
