package pl.tcs.oopproject.model.connection;

import pl.tcs.oopproject.model.discount.Price;
import pl.tcs.oopproject.model.discount.PricePLN;
import pl.tcs.oopproject.model.station.Station;

import java.util.List;

public class ScheduledTrain implements RouteElement { //some kind of decorator to RouteStops
    private final String company;
    private final RouteStops connection;
    private final int number; //number of a train
    private Price cost;
    private final ReservationType trainType;

    public ScheduledTrain(String company, int number, double cost, ReservationType trainType, RouteStops connection) {
        this.company = company;
        this.connection = connection;
        this.number = number;
        this.trainType = trainType;
        this.cost = new PricePLN(cost);
    }

    public void setCost(Price cost) {
        this.cost = cost;
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
        if (!(obj instanceof ScheduledTrain)) return false;
        return number == ((ScheduledTrain) obj).number;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Company: ").append(company).append("\n");
        sb.append("Number: ").append(number).append("\n");
        sb.append("Cost: ").append(cost).append("\n");
        sb.append("Train Type: ").append(trainType.name()).append("\n");
        sb.append("Stations:\n");
        for (Station station : connection.stations()) {
            sb.append(station.toString()).append("\n");
        }
        return sb.toString();
    }
}
