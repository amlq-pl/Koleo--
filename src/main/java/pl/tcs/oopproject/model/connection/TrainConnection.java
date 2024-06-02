package pl.tcs.oopproject.model.connection;

import pl.tcs.oopproject.model.station.Station;

import java.util.ArrayList;
import java.util.List;

public class TrainConnection implements ConnectionInterface {
	private final ArrayList<Station> train;
	
	public TrainConnection(ArrayList<Station> train) {
		this.train = train;
	}
	
	public boolean contains(String town) {
		for (Station s : train)
			if (s.getTown().equals(town)) return true;
		return false;
	}
	
	public void add(Station station) {
		if (contains(station.getTown())) throw new IllegalArgumentException();
		train.add(station);
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
	
	public int getIndexOfStation(String town) throws IllegalArgumentException {
		for (int i = 0; i < train.size(); ++i)
			if (town.equals(train.get(i).getTown())) return i;
		throw new IllegalArgumentException();
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
		if (!contains(station)) return null;
		return train.get(getIndexOfStation(station));
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof TrainConnection)) return false;
		TrainConnection t = (TrainConnection) obj;
		if(t.getSize() != getSize()) return false;
		for(int i = 0; i < t.getSize(); ++i)
			if(t.getStations().get(i) != getStations().get(i))
				return false;
		return true;
 	}
}
