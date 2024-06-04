package pl.tcs.oopproject.model.connection;

import pl.tcs.oopproject.model.discount.Price;
import pl.tcs.oopproject.model.discount.PricePLN;
import pl.tcs.oopproject.model.station.Station;

import java.util.List;

public class ScheduledTrain implements RouteElement { //some kind of decorator to RouteStops
	private final String company;
	private final RouteStops connection;
	private final int number; //number of a train
	private final Price cost;
	private final ReservationType trainType;
	
	public ScheduledTrain(String company, int number, double cost, ReservationType trainType, RouteStops connection) {
		this.company = company;
		this.connection = connection;
		this.number = number;
		this.trainType = trainType;
		this.cost = new PricePLN(cost);
	}
	
	public int getNumber() {
		return number;
	}
	
	public String getCompany() {
		return company;
	}
	
	public ReservationType getTrainType() {
		return trainType;
	}
	
	@Override
	public Station originStation() {
		return connection.originStation();
	}
	
	@Override
	public Station destinationStation() {
		return connection.destinationStation();
	}
	
	@Override
	public Station getStation(int index) throws IndexOutOfBoundsException {
		return connection.getStation(index);
	}
	
	public int getIndexOfStation(String town) throws IllegalArgumentException {
		return connection.getIndexOfStation(town);
	}
	
	@Override
	public List<Station> stations() {
		return connection.stations();
	}
	
	public Price getCost() {
		return cost;
	}
	
	@Override
	public int numberOfStops() {
		return connection.numberOfStops();
	}
	
	public boolean contains(String town) {
		return connection.contains(town);
	}
	
	public Station getStation(String station) {
		return connection.getStation(station);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ScheduledTrain)) return false;
		return number == ((ScheduledTrain) obj).number;
	}
	
	public void display() {
		System.out.println("Company: " + company);
		System.out.println("Number: " + number);
		System.out.println("Cost: " + cost);
		System.out.println("Train Type: " + trainType.name());
		System.out.println("Stations:");
		for (Station station : connection.stations()) {
			station.toString();
		}
	}
}
