package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.Station;

import java.util.List;

public interface ConnectionInterface {
	Station getFirstStation();
	
	Station getLastStation();
	
	Station getStationAt(int index) throws IndexOutOfBoundsException;
	
	List<Station> getStations();
	
	int getSize();
	
}

