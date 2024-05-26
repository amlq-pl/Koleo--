
import org.junit.Test;
import pl.tcs.oopproject.viewmodel.connection.*;
import pl.tcs.oopproject.viewmodel.exception.NoRouteFoundException;
import pl.tcs.oopproject.viewmodel.station.Station;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class TrainTest {
	
	public class Finder implements FindConnectionInterface {
		private static final int maxTransferNumber = 4;
		private static final int hours = 8;
		private final ArrayList<ConnectionWithTransfers> trains = new ArrayList<>();
		private final String stationA;
		private final String stationB;
		private final LocalDateTime departureDate;
		
		public Finder(String stationA, String stationB, LocalDateTime departureDate) {
			this.stationA = stationA;
			this.stationB = stationB;
			this.departureDate = departureDate;
		}
		
		private void findConnection(
				ArrayList<DirectConnection> allTrains, String temp,
				ArrayList<DirectConnection> stack, ArrayList<String> transfersStack,
				HashSet<DirectConnection> visitedConnections, HashSet<String> visitedStations) {
			for (int i = 0; i < allTrains.size(); ++i) {
				DirectConnection tempConnection = allTrains.get(i);
				if (tempConnection.contains(temp) && !visitedConnections.contains(tempConnection)) {
					stack.add(tempConnection);
					visitedConnections.add(tempConnection);
					transfersStack.add(temp);
					if (tempConnection.contains(stationB) && tempConnection.getIndexOfStation(stationB) > tempConnection.getIndexOfStation(temp)) {
						Station sA = new Station(stationA,
								stack.get(0).getStation(stationA).getDepartureTime(),
								stack.get(0).getStation(stationA).getArrivalTime());
						Station sB = new Station(stationB,
								stack.get(stack.size() - 1).getStation(stationB).getDepartureTime(),
								stack.get(stack.size() - 1).getStation(stationB).getArrivalTime());
						trains.add(new ConnectionWithTransfers(sA, sB, stack, transfersStack));
						stack.remove(stack.size() - 1);
						visitedConnections.remove(tempConnection);
						transfersStack.remove(transfersStack.size() - 1);
						return;
					} //good
					
					if (stack.size() >= maxTransferNumber) {
						stack.remove(stack.size() - 1);
						transfersStack.remove(transfersStack.size() - 1);
						visitedConnections.remove(tempConnection);
						return;
					}
					
					int index = tempConnection.getIndexOfStation(temp);
					
					for (int j = index + 1; j < tempConnection.getSize(); ++j) {
						visitedStations.add(tempConnection.getStationAt(j).getTown());
						if(!visitedStations.contains(tempConnection.getStationAt(j).getTown())) {
							findConnection(allTrains, tempConnection.getStationAt(j).getTown(), stack, transfersStack, visitedConnections, visitedStations);
						}
						else break;
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
		
		public void setTrains(List<DirectConnection> l) {
			ArrayList<DirectConnection> allTrains = new ArrayList<>(l);
			ArrayList<DirectConnection> stack = new ArrayList<>();
			ArrayList<String> transferStack = new ArrayList<>();
			HashSet<DirectConnection> visitedDirectConnection = new HashSet<>();
			HashSet<String> visitedStation = new HashSet<>();
			findConnection(allTrains, stationA, stack, transferStack, visitedDirectConnection, visitedStation);
		}
		
		@Override
		public List<ConnectionWithTransfers> getRoutes() {
			Collections.sort(trains);
			ArrayList<ConnectionWithTransfers> connections = new ArrayList<>();
			int size = Math.min(5, trains.size());
			for (int i = 0; i < size; ++i)
				connections.add(trains.get(i));
			
			return connections;
		}
		
		@Override
		public List<ConnectionWithTransfers> getCheapRoutes(){
			ArrayList<ConnectionWithTransfers> connections = new ArrayList<>();
			int size = Math.min(5, trains.size());
			for (int i = 0; i < size; ++i) {
				for (int j = 1; j < trains.size(); ++j) {
					if (trains.get(i).getCost().getPriceValue() > trains.get(j).getCost().getPriceValue()) {
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
		public List<ConnectionWithTransfers> getRoutesWithoutTransfers() {
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
	
	@Test
	public void Test1() {
		Station station1 = new Station("Kraków", LocalDateTime.of(2024, 5, 27, 5, 12), LocalDateTime.of(2024, 5, 27, 5, 14));
		Station station2 = new Station("Warszawa", LocalDateTime.of(2024, 5, 27, 6, 12), LocalDateTime.of(2024, 5, 27, 6, 17));
		Station station3 = new Station("Szczecin", LocalDateTime.now(), LocalDateTime.now());

		DirectConnection dC1= new DirectConnection("TCS", 12, 21.37, TrainIsReservation.WITH_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station1, station2))));
		DirectConnection dC2 = new DirectConnection("UJ", 13, 12.34, TrainIsReservation.WITH_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station2, station3))));
		
		Finder finder = new Finder("Kraków", "Warszawa", LocalDateTime.now());
		finder.setTrains(List.of(dC1, dC2));
		List<ConnectionWithTransfers> connections = finder.getRoutes();
		for(ConnectionWithTransfers x : connections)
			System.out.println(x.getCost());
	}
	
}