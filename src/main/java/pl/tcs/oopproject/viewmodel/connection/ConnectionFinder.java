package pl.tcs.oopproject.viewmodel.connection;

import pl.tcs.oopproject.postgresDatabaseIntegration.GetDirectConnectionsInTimeframe;
import pl.tcs.oopproject.viewmodel.exception.NoRouteFoundException;
import pl.tcs.oopproject.viewmodel.station.Station;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConnectionFinder implements FindConnectionInterface {
	private static final int hours = 8;
	private final ArrayList<ConnectionWithTransfers> trains = new ArrayList<>();
	private final String stationA;
	private final String stationB;
	private final LocalDateTime departureDate;
	private boolean active = true;
	
	public ConnectionFinder(String stationA, String stationB, LocalDateTime departureDate) {
		this.stationA = stationA;
		this.stationB = stationB;
		this.departureDate = departureDate;
	}
	
	private void findConnection(ArrayList<DirectConnection> allTrains, String temp, ArrayList<DirectConnection> stack, ArrayList<String> transfersStack) {
		for (int i = 0; i < allTrains.size(); ++i) {
			if (allTrains.get(i).contains(temp)) {
				stack.add(allTrains.get(i));
				transfersStack.add(temp);
				if (allTrains.get(i).contains(stationB)) {
					trains.add(new ConnectionWithTransfers(new Station(stationA, stack.get(0).getStation(stationA).getDepartureTime(), stack.get(0).getStation(stationA).getArrivalTime()), new Station(stationB, stack.get(stack.size() - 1).getStation(stationB).getDepartureTime(), stack.get(stack.size() - 1).getStation(stationB).getArrivalTime()), stack, transfersStack));
					stack.remove(stack.size() - 1);
					transfersStack.remove(transfersStack.size() - 1);
					return;
				} //good
				
				if (stack.size() >= 4) {
					stack.remove(stack.size() - 1);
					transfersStack.remove(transfersStack.size() - 1);
					return;
				}
				
				int index = allTrains.get(i).getIndexOfStation(temp);
				DirectConnection connection = allTrains.get(i);
				
				for (int j = index + 1; j < connection.getSize(); ++j) {
					findConnection(allTrains, connection.getStationAt(j).getTown(), stack, transfersStack);
				}
				stack.remove(stack.size() - 1);
				transfersStack.remove(transfersStack.size() - 1);
			}
		}
	}
	
	private void setTrains() throws SQLException {
		ArrayList<DirectConnection> allTrains = new GetDirectConnectionsInTimeframe().getDirectConnectionsInTimeframe(departureDate, departureDate.plusHours(hours));
		ArrayList<DirectConnection> stack = new ArrayList<>();
		ArrayList<String> transferStack = new ArrayList<>();
		findConnection(allTrains, stationA, stack, transferStack);
		active = false;
	}
	
	@Override
	public List<ConnectionWithTransfers> getRoutes() throws SQLException {
		if (!active) setTrains();
		Collections.sort(trains);
		ArrayList<ConnectionWithTransfers> connections = new ArrayList<>();
		int size = Math.min(5, trains.size());
		for (int i = 0; i < size; ++i)
			connections.add(trains.get(i));
		
		return connections;
	}
	
	@Override
	public List<ConnectionWithTransfers> getCheapRoutes() throws SQLException {
		if (!active) setTrains();
		ArrayList<ConnectionWithTransfers> connections = new ArrayList<>();
		int size = Math.min(5, trains.size());
		for (int i = 0; i < size; ++i) {
			for (int j = 1; j < trains.size(); ++j) {
				if (trains.get(i).getCost() > trains.get(j).getCost()) {
					ConnectionWithTransfers temp = trains.get(i);
					trains.set(i, trains.get(j));
					trains.set(j, temp);
				}
			}
		} //TO DO: check it
		
		for (int i = 0; i < size; ++i)
			connections.add(trains.get(i));
		
		return connections;
	}
	
	@Override
	public List<ConnectionWithTransfers> getRoutesWithoutTransfers() throws SQLException {
		if (!active) setTrains();
		
		ArrayList<ConnectionWithTransfers> connection = new ArrayList<>();
		for (ConnectionWithTransfers train : trains)
			if (train.getNumberOfTransfers() == 0) connection.add(train);
		
		if (connection.isEmpty()) throw new NoRouteFoundException();
		Collections.sort(connection);
		int size = Math.min(5, trains.size());
		for (int i = 0; i < size; ++i)
			connection.add(trains.get(i));
		
		return connection;
	}
}
