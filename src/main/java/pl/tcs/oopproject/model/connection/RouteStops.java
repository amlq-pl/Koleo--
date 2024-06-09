package pl.tcs.oopproject.model.connection;

import pl.tcs.oopproject.model.station.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RouteStops implements RouteElement {
    private final ArrayList<Station> train;

    public RouteStops(ArrayList<Station> train) {
        this.train = train;
    }

    public boolean contains(String town) {
        for (Station s : train)
            if (s.town().equals(town)) return true;
        return false;
    }

    public void add(Station station) {
        if (contains(station.town())) throw new IllegalArgumentException();
        train.add(station);
    }

    @Override
    public Station originStation() {
        return train.get(0);
    }

    @Override
    public Station destinationStation() {
        return train.get(train.size() - 1);
    }

    @Override
    public Station getStation(int index) throws IndexOutOfBoundsException {
        return train.get(index);
    }

    public int getIndexOfStation(String town) throws IllegalArgumentException {
        for (int i = 0; i < train.size(); ++i)
            if (town.equals(train.get(i).town())) return i;
        throw new IllegalArgumentException();
    }

    @Override
    public List<Station> stations() {
        return train;
    }

    @Override
    public int numberOfStops() {
        return train.size();
    }

    public Station getStation(String station) {
        if (!contains(station)) return null;
        return train.get(getIndexOfStation(station));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RouteStops t)) return false;
        if (t.numberOfStops() != numberOfStops()) return false;
        for (int i = 0; i < t.numberOfStops(); ++i) {
            //	System.out.println(t.stations().get(i).town() + " ~ " + stations().get(i).town());
            if (!Objects.equals(t.stations().get(i).town(), stations().get(i).town())) {
                return false;
            }
        }
        return true;
    }
}
