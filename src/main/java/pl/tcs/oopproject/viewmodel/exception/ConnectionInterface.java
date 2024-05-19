package pl.tcs.oopproject.viewmodel.exception;

import pl.tcs.oopproject.viewmodel.station.StationInterface;
import java.util.Iterator;

public interface ConnectionInterface {
	
	StationInterface getFirstStation();
	
	StationInterface getLastStation();
	
	StationInterface getStationAt(int index) throws IndexOutOfBoundsException;
	
	int IndexOfStation(String town) throws IllegalArgumentException;
	
	Iterator<StationInterface> getIterator();
}
