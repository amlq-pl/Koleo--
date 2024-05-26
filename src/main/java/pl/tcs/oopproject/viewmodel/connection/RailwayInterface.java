package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.Station;

import java.util.Iterator;
import java.util.List;

public interface RailwayInterface {
	
	TrainType getTrainType();
	
	Station getFirstStation();
	
	Station getLastStation();
	
	Station getStationAt(int index) throws IndexOutOfBoundsException;
	
	int getIndexOfStation(String town) throws IllegalArgumentException;
	
	Iterator<Station> getIterator();
	
	List<Station> getStations();
	
	int getCost();
	
	int getSize();
}

