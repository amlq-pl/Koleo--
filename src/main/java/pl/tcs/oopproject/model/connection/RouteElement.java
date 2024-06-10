package pl.tcs.oopproject.model.connection;

import pl.tcs.oopproject.model.station.Station;

import java.util.List;

public interface RouteElement {
    Station originStation();

    Station destinationStation();

    Station getStation(int index) throws IndexOutOfBoundsException;

    List<Station> stations();

    int numberOfStops();

}