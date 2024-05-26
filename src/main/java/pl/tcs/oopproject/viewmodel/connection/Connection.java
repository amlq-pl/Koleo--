package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.Station;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Connection implements ConnectionInterface {
	private final ArrayList<Station> train;
	
	Connection(ArrayList<Station> train) {
		this.train = train;
	}
	
	public boolean contains(String town) {
		for(Station s : train)
			if(s.getTown().equals(town))
				return true;
		return false;
	}
	
	@Override
	public Station getFirstStation() {
		return train.get(0);
	}
	
	@Override
	public Station getLastStation() {
		return train.get(train.size() - 1);
	}
	
	@Override
	public Station getStationAt(int index) throws IndexOutOfBoundsException {
		return train.get(index);
	}
	@Override
	public int getIndexOfStation(String town) throws IllegalArgumentException {
		for (int i = 0; i < train.size(); ++i)
			if (town.equals(train.get(i).getTown()))
				return i;
		throw new IllegalArgumentException();
	}
	@Override
	public Iterator<Station> getIterator() {
		return train.listIterator();
	}
	
	@Override
	public List<Station> getStations() {
		return train;
	}
	
	@Override
	public int getSize() {
		return train.size();
	}
	
	public Station getStation(String station) {
		if(!contains(station)) return null;
		return train.get(getIndexOfStation(station));
	}
	
}
