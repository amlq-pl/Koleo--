package pl.tcs.oopproject.viewmodel.connection;

import org.jetbrains.annotations.NotNull;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.connection.ScheduledTrain;
import pl.tcs.oopproject.model.exception.NoRouteFoundException;
import pl.tcs.oopproject.model.station.Station;
import pl.tcs.oopproject.postgresDatabaseIntegration.GetDirectConnectionsInTimeframe;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ConnectionFinder implements FindConnectionInterface {
	private static final int maxTransferNumber = 4;
	private static final int hours = 16;
	private final ArrayList<MultiStopRoute> trains = new ArrayList<>();
	private final String stationA;
	private final String stationB;
	private final LocalDateTime departureDate;
	private boolean active = false;
	
	private ConnectionFinder(String stationA, String stationB, LocalDateTime departureDate) {
		this.stationA = stationA;
		this.stationB = stationB;
		this.departureDate = departureDate;
	}
	
	public static ConnectionFinder getConnectionFinder(String stationA, String stationB, LocalDateTime departureDate) throws SQLException {
		ConnectionFinder finder = new ConnectionFinder(stationA, stationB, departureDate);
		finder.setTrains();
		return finder;
	}
	
	private boolean contains(ArrayList<ScheduledTrain> stack) {
		for (MultiStopRoute connection : trains) {
			if (connection.numberOfTransfers() + 1 != stack.size()) continue;
			boolean equal = true;
			for (int i = 0; i < stack.size(); ++i) {
				if (stack.get(i) != connection.getTrains().get(i)) {
					equal = false;
					break;
				}
			}
			if (equal) return true;
		}
		return false;
	}
	
	private void findConnection(ArrayList<ScheduledTrain> allTrains, String temp, ArrayList<ScheduledTrain> stack, ArrayList<String> transfersStack, HashSet<ScheduledTrain> visitedConnections, HashSet<String> visitedStations) {
		for (int i = 0; i < allTrains.size(); ++i) {
			ScheduledTrain tempConnection = allTrains.get(i);
			if (tempConnection.contains(temp) && !visitedConnections.contains(tempConnection)) {
				if (!stack.isEmpty()) {
					LocalDateTime departureTime = stack.get(stack.size() - 1).getStation(temp).departureTime();
					if (tempConnection.getStation(temp).arrivalTime().isBefore(departureTime)) continue;
				}
				stack.add(tempConnection);
				visitedConnections.add(tempConnection);
				transfersStack.add(temp);
				if (tempConnection.contains(stationB) && tempConnection.getIndexOfStation(stationB) > tempConnection.getIndexOfStation(temp)) {
					Station sA = new Station(stationA, stack.get(0).getStation(stationA).departureTime(), stack.get(0).getStation(stationA).arrivalTime());
					Station sB = new Station(stationB, stack.get(stack.size() - 1).getStation(stationB).departureTime(), stack.get(stack.size() - 1).getStation(stationB).arrivalTime());
					if (!contains(stack))
						trains.add(new MultiStopRoute(sA, sB, new ArrayList<>(stack), new ArrayList<>(transfersStack)));
					
					stack.remove(stack.size() - 1);
					visitedConnections.remove(tempConnection);
					transfersStack.remove(transfersStack.size() - 1);
					continue;
				}
				
				if (stack.size() >= maxTransferNumber) {
					stack.remove(stack.size() - 1);
					transfersStack.remove(transfersStack.size() - 1);
					visitedConnections.remove(tempConnection);
					return;
				}
				
				int index = tempConnection.getIndexOfStation(temp);
				
				for (int j = index + 1; j < tempConnection.numberOfStops(); ++j) {
					if (!visitedStations.contains(tempConnection.getStation(j).town())) {
						visitedStations.add(tempConnection.getStation(j).town());
						findConnection(allTrains, tempConnection.getStation(j).town(), stack, transfersStack, visitedConnections, visitedStations);
					} else break;
				}
				
				for (int j = index + 1; j < tempConnection.numberOfStops(); ++j) {
					visitedStations.remove(tempConnection.getStation(j).town());
				}
				stack.remove(stack.size() - 1);
				visitedConnections.remove(tempConnection);
				transfersStack.remove(transfersStack.size() - 1);
			}
		}
	}
	
	private void setTrains() throws SQLException {
		ArrayList<ScheduledTrain> allTrains = new GetDirectConnectionsInTimeframe().getDirectConnectionsInTimeframe(departureDate, departureDate.plusHours(hours));
		ArrayList<ScheduledTrain> stack = new ArrayList<>();
		ArrayList<String> transferStack = new ArrayList<>();
		HashSet<ScheduledTrain> visitedScheduledTrain = new HashSet<>();
		HashSet<String> visitedStation = new HashSet<>();
		visitedStation.add(stationA);
		findConnection(allTrains, stationA, stack, transferStack, visitedScheduledTrain, visitedStation);
		active = false;
	}
	
	@Override
	public List<MultiStopRoute> getRoutes() throws SQLException {
		if (!active) setTrains();
		return getConnectionWithTransfers(trains);
	}
	
	@NotNull
	private List<MultiStopRoute> getConnectionWithTransfers(ArrayList<MultiStopRoute> trains) {
		Collections.sort(trains);
		ArrayList<MultiStopRoute> connections = new ArrayList<>();
		int size = Math.min(5, trains.size());
		for (int i = 0; i < size; ++i)
			connections.add(trains.get(i));
		
		return connections;
	}
	
	@Override
	public List<MultiStopRoute> getCheapRoutes() throws SQLException {
		if (!active) setTrains();
		ArrayList<MultiStopRoute> connections = new ArrayList<>();
		int size = Math.min(5, trains.size());
		for (int i = 0; i < size; ++i) {
			for (int j = i + 1; j < trains.size(); ++j) {
				if (trains.get(i).cost().getPriceValue() > trains.get(j).cost().getPriceValue()) {
					Collections.swap(trains, i, j);
				}
			}
		}
		
		for (int i = 0; i < size; ++i)
			connections.add(trains.get(i));
		
		return connections;
	}
	
	@Override
	public List<MultiStopRoute> getRoutesWithoutTransfers() throws SQLException {
		if (!active) setTrains();
		
		ArrayList<MultiStopRoute> connection = new ArrayList<>();
		for (MultiStopRoute train : trains)
			if (train.numberOfTransfers() == 0) connection.add(train);
		
		if (connection.isEmpty()) throw new NoRouteFoundException();
		return getConnectionWithTransfers(connection);
	}
}
