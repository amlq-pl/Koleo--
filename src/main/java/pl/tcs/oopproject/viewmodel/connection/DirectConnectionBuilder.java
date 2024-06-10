package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.model.connection.ReservationType;
import pl.tcs.oopproject.model.connection.RouteStops;
import pl.tcs.oopproject.model.connection.ScheduledTrain;
import pl.tcs.oopproject.model.exception.TooFewArgumentsException;
import pl.tcs.oopproject.model.station.Station;

import java.util.ArrayList;

public class DirectConnectionBuilder {
    private RouteStops connection = null;
    private String company = "";
    private int number = -1;
    private double cost = 0;
    private ReservationType trainType;


    public DirectConnectionBuilder() {
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setConnection(RouteStops connection) {
        this.connection = connection;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTrainType(ReservationType trainType) {
        this.trainType = trainType;
    }

    public void addStation(Station station) {
        if (connection == null) connection = new RouteStops(new ArrayList<>());
        connection.add(station);
    }

    public void addCost(double cost) {
        this.cost += cost;
    }

    public ScheduledTrain getTrainConnection() throws TooFewArgumentsException {
        if (number == -1 || cost == 0 || trainType == null || connection == null || company.isEmpty())
            throw new TooFewArgumentsException();
        return new ScheduledTrain(company, number, cost, trainType, connection);
    }
}

