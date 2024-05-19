package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.StationInterface;

import java.util.Iterator;
import java.util.List;

public class DirectConnection implements RailwayInterface { //some kind of decorator to Connection
	private final String company;
	private final Connection connection;
	private final int number; //number of a train
	private final int cost;
	private final TrainType trainType;
	
	DirectConnection(String company, int number, int cost, Connection connection, TrainType trainType) {
		this.company = company;
		this.connection = connection;
		this.number = number;
		this.cost = cost;
		this.trainType = trainType;
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
	public StationInterface getFirstStation() {
		return connection.getFirstStation();
	}
	
	@Override
	public StationInterface getLastStation() {
		return connection.getLastStation();
	}
	
	@Override
	public StationInterface getStationAt(int index) throws IndexOutOfBoundsException {
		return connection.getStationAt(index);
	}
	
	@Override
	public int IndexOfStation(String town) throws IllegalArgumentException {
		return connection.IndexOfStation(town);
	}
	
	@Override
	public Iterator<StationInterface> getIterator() {
		return connection.getIterator();
	}
	
	@Override
	public List<StationInterface> getStations() {
		return connection.getStations();
	}
	
	public int getCost() {
		return cost;
	}
	
}
