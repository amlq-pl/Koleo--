package pl.tcs.oopproject.viewmodel.connection;

import org.jetbrains.annotations.NotNull;
import pl.tcs.oopproject.postgresDatabaseIntegration.GetDirectConnectionsInTimeframe;
import pl.tcs.oopproject.viewmodel.exception.NoRouteFoundException;
import pl.tcs.oopproject.viewmodel.station.Station;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ConnectionFinder implements FindConnectionInterface {
	private static final int maxTransferNumber = 4;
	private static final int hours = 20;
	private ArrayList<ConnectionWithTransfers> trains;
	private final String stationA;
	private final String stationB;
	private final LocalDateTime departureDate;
	private boolean active = false;
	
	private ConnectionFinder(String stationA, String stationB, LocalDateTime departureDate) {
		this.stationA = stationA;
		this.stationB = stationB;
		this.departureDate = departureDate;
	}
	
	public ConnectionFinder getConnectionFinder(String stationA, String stationB, LocalDateTime departureDate) throws SQLException {
		ConnectionFinder finder = new ConnectionFinder(stationA, stationB, departureDate);
		finder.setTrains();
		return finder;
	}
	
	private boolean contains(ArrayList<DirectConnection> stack) {
		for (ConnectionWithTransfers connection : trains) {
			if (connection.getNumberOfTransfers() + 1 != stack.size()) continue;
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
	
	private void findConnection(ArrayList<DirectConnection> allTrains, String temp, ArrayList<DirectConnection> stack, ArrayList<String> transfersStack, HashSet<DirectConnection> visitedConnections, HashSet<String> visitedStations) {
		for (int i = 0; i < allTrains.size(); ++i) {
			DirectConnection tempConnection = allTrains.get(i);
			if (tempConnection.contains(temp) && !visitedConnections.contains(tempConnection)) {
				if (!stack.isEmpty()) {
					LocalDateTime departureTime = stack.get(stack.size() - 1).getStation(temp).getDepartureTime();
					if (tempConnection.getStation(temp).getArrivalTime().isBefore(departureTime)) continue;
				}
				stack.add(tempConnection);
				visitedConnections.add(tempConnection);
				transfersStack.add(temp);
				if (tempConnection.contains(stationB) && tempConnection.getIndexOfStation(stationB) > tempConnection.getIndexOfStation(temp)) {
					Station sA = new Station(stationA, stack.get(0).getStation(stationA).getDepartureTime(), stack.get(0).getStation(stationA).getArrivalTime());
					Station sB = new Station(stationB, stack.get(stack.size() - 1).getStation(stationB).getDepartureTime(), stack.get(stack.size() - 1).getStation(stationB).getArrivalTime());
					if (!contains(stack))
						trains.add(new ConnectionWithTransfers(sA, sB, new ArrayList<>(stack), new ArrayList<>(transfersStack)));
					
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
				
				for (int j = index + 1; j < tempConnection.getSize(); ++j) {
					if (!visitedStations.contains(tempConnection.getStationAt(j).getTown())) {
						visitedStations.add(tempConnection.getStationAt(j).getTown());
						findConnection(allTrains, tempConnection.getStationAt(j).getTown(), stack, transfersStack, visitedConnections, visitedStations);
					} else break;
				}
				
				for (int j = index + 1; j < tempConnection.getSize(); ++j) {
					visitedStations.remove(tempConnection.getStationAt(j).getTown());
				}
				stack.remove(stack.size() - 1);
				visitedConnections.remove(tempConnection);
				transfersStack.remove(transfersStack.size() - 1);
			}
		}
	}
	
	private void setTrains() throws SQLException {
		ArrayList<DirectConnection> allTrains = new GetDirectConnectionsInTimeframe().getDirectConnectionsInTimeframe(departureDate, departureDate.plusHours(hours));
		ArrayList<DirectConnection> stack = new ArrayList<>();
		ArrayList<String> transferStack = new ArrayList<>();
		HashSet<DirectConnection> visitedDirectConnection = new HashSet<>();
		HashSet<String> visitedStation = new HashSet<>();
		findConnection(allTrains, stationA, stack, transferStack, visitedDirectConnection, visitedStation);
		active = false;
	}
	
	@Override
	public List<ConnectionWithTransfers> getRoutes() throws SQLException {
		if (!active) setTrains();
		return getConnectionWithTransfers(trains);
	}
	
	@NotNull
	private List<ConnectionWithTransfers> getConnectionWithTransfers(ArrayList<ConnectionWithTransfers> trains) {
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
			for (int j = i + 1; j < trains.size(); ++j) {
				if (trains.get(i).getCost().getPriceValue() > trains.get(j).getCost().getPriceValue()) {
					Collections.swap(trains, i, j);
				}
			}
		}
		
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
		return getConnectionWithTransfers(connection);
	}
}
