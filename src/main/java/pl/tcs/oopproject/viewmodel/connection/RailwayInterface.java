package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.StationInterface;

import java.util.Iterator;
import java.util.List;

public interface RailwayInterface {
	
	public TrainType getTrainType();
	
	public StationInterface getFirstStation();
	
	public StationInterface getLastStation();
	
	public StationInterface getStationAt(int index) throws IndexOutOfBoundsException;
	
	public int IndexOfStation(String town) throws IllegalArgumentException;
	
	public Iterator<StationInterface> getIterator();
	
	public List<StationInterface> getStations();
	
	public int getCost();
}

