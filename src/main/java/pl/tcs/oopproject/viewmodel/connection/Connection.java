package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.StationInterface;

import java.util.Iterator;
import java.util.List;

public class Connection implements Railway{
	List<StationInterface> train;
	
	Connection(List<StationInterface> train) {
		this.train = train;
	}
	
	@Override
	public StationInterface getFirstStation() {
		return train.get(0);
	}
	
	@Override
	public StationInterface getLastStation() {
		return train.get(train.size() - 1);
	}
	
	@Override
	public StationInterface getStationAt(int index) throws IndexOutOfBoundsException {
		return train.get(index);
	}
	@Override
	public int IndexOfStation(String town) throws IllegalArgumentException {
		for (int i = 0; i < train.size(); ++i)
			if (town.equals(train.get(i).getTown()))
				return i;
		throw new IllegalArgumentException();
	}
	@Override
	public Iterator<StationInterface> getIterator() {
		return train.listIterator();
	}
	
	@Override
	public List<StationInterface> getStations() {
		return train;
	}
	
}
