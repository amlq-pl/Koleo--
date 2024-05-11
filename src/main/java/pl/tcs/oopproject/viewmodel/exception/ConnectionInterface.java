package pl.tcs.oopproject.viewmodel.exception;

import pl.tcs.oopproject.viewmodel.station.StationInterface;
import java.util.Iterator;

public interface ConnectionInterface {
	
	public StationInterface getFirstStation();
	
	public StationInterface getLastStation();
	
	public StationInterface getStationAt(int index) throws IndexOutOfBoundsException;
	
	public int IndexOfStation(String town) throws IllegalArgumentException;
	
	public Iterator<StationInterface> getIterator();
}
