package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.viewmodel.station.StationInterface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Connection implements RailwayInterface {
	ArrayList<StationInterface> train;
	int cost;
	TrainType trainType;
	
	Connection(ArrayList<StationInterface> train, TrainType trainType, int cost) {
		this.train = train;
		this.trainType = trainType;
		this.cost = cost;
	}
	
	public boolean contains(String town) {
		for(StationInterface s : train)
			if(s.getTown().equals(town))
				return true;
		return false;
	}
	
	public boolean contains(String town, int after) {
		for(int i = after; i < train.size(); ++i)
			if(Objects.equals(train.get(i).getTown(), town))
				return true;
		return false;
	}
	
	@Override
	public TrainType getTrainType() {
		return trainType;
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
	
	@Override
	public int getCost() {
		return cost;
	}
	
	@Override
	public int getSize() {
		return train.size();
	}
	
}
