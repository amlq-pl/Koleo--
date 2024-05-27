package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.model.connection.DirectConnection;
import pl.tcs.oopproject.model.connection.TrainConnection;
import pl.tcs.oopproject.model.connection.TrainIsReservation;
import pl.tcs.oopproject.model.exception.TooFewArgumentsException;
import pl.tcs.oopproject.model.station.Station;

import java.util.ArrayList;

public class DirectConnectionBuilder {
	private TrainConnection connection = null;
	private String company = "";
	private int number = -1;
	private double cost = 0;
	private TrainIsReservation trainType;
	
	
	public DirectConnectionBuilder() {
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public void setConnection(TrainConnection connection) {
		this.connection = connection;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public void setTrainType(TrainIsReservation trainType) {
		this.trainType = trainType;
	}
	
	public void addStation(Station station) {
		if (connection == null) connection = new TrainConnection(new ArrayList<>());
		connection.add(station);
	}
	
	public void addCost(double cost) {
		this.cost += cost;
	}
	
	public DirectConnection getTrainConnection() throws TooFewArgumentsException {
		if (number == -1 || cost == 0 || trainType == null || connection == null || company.isEmpty())
			throw new TooFewArgumentsException();
		return new DirectConnection(company, number, cost, trainType, connection);
	}
}

