package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.StationInterface;

import java.util.Iterator;
import java.util.List;

public interface RailwayInterface {
	
	TrainType getTrainType();
	
	StationInterface getFirstStation();
	
	StationInterface getLastStation();
	
	StationInterface getStationAt(int index) throws IndexOutOfBoundsException;
	
	int getIndexOfStation(String town) throws IllegalArgumentException;
	
	Iterator<StationInterface> getIterator();
	
	List<StationInterface> getStations();
	
	int getCost();
	
	int getSize();
}

