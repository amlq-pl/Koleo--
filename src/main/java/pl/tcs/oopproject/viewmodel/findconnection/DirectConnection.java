package pl.tcs.oopproject.viewmodel.findconnection;

import java.util.Iterator;
import java.util.List;

public abstract class DirectConnection {
	List<Station> trail;
	String company;
	
	DirectConnection() {
	}
	
	public Station getFirstStation() {
		return trail.get(0);
	}
	
	public Station getLastStation() {
		return trail.get(trail.size() - 1);
	}
	
	public Station getStationAt(int index) throws IndexOutOfBoundsException {
		return trail.get(index);
	}
	
	public int IndexOfStation(String town) throws IllegalArgumentException {
		for (int i = 0; i < trail.size(); ++i)
			if (town.equals(trail.get(i).getTown()))
				return i;
		throw new IllegalArgumentException();
	}
	
	public Iterator<Station> getIterator() {
		return trail.listIterator();
	}
	
	public String getCompany() {
		return company;
	}
	
}
