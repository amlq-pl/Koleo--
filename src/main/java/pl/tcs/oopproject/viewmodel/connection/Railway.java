package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.StationInterface;

import java.util.Iterator;
import java.util.List;

public interface Railway {
	
	public StationInterface getFirstStation();
	
	public StationInterface getLastStation();
	
	public StationInterface getStationAt(int index) throws IndexOutOfBoundsException;
	
	public int IndexOfStation(String town) throws IllegalArgumentException;
	
	public Iterator<StationInterface> getIterator();
	
	public List<StationInterface> getStations();
}
