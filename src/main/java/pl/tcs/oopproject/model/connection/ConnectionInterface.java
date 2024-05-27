package pl.tcs.oopproject.model.connection;

import pl.tcs.oopproject.model.station.Station;

import java.util.List;

public interface ConnectionInterface {
	Station getFirstStation();
	
	Station getLastStation();
	
	Station getStationAt(int index) throws IndexOutOfBoundsException;
	
	List<Station> getStations();
	
	int getSize();
	
}

