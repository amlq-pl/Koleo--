import org.junit.Test;
import pl.tcs.oopproject.viewmodel.connection.*;
import pl.tcs.oopproject.viewmodel.exception.NoRouteFoundException;
import pl.tcs.oopproject.viewmodel.station.Station;
import java.time.LocalDateTime;
import java.util.*;

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
				//tempConnection.display();
				
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
						trains.add(new ConnectionWithTransfers(sA, sB, new ArrayList<>(stack), new ArrayList<>(transfersStack)));
						stack.remove(stack.size() - 1);
						visitedConnections.remove(tempConnection);
						transfersStack.remove(transfersStack.size() - 1);
						continue;
					} //good
					
					if (stack.size() >= maxTransferNumber) {
						stack.remove(stack.size() - 1);
						transfersStack.remove(transfersStack.size() - 1);
						visitedConnections.remove(tempConnection);
						return;
					}
					
					int index = tempConnection.getIndexOfStation(temp);
					for (int j = index + 1; j < tempConnection.getSize(); ++j) {
						LocalDateTime arrivalTime = tempConnection.getStationAt(j).getArrivalTime();
						LocalDateTime departureTime = stack.get(stack.size() - 1).getStation(temp).getDepartureTime();
						if(!visitedStations.contains(tempConnection.getStationAt(j).getTown()) && !arrivalTime.isBefore(departureTime)) {
							visitedStations.add(tempConnection.getStationAt(j).getTown());
							visitedStations.add(tempConnection.getStationAt(j).getTown());
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
			visitedStation.add(stationA);
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
			System.out.println("GET ROUTES");
			ArrayList<ConnectionWithTransfers> connections = new ArrayList<>();
			System.out.println("TO SORT");
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
		public List<ConnectionWithTransfers> getRoutesWithoutTransfers() {
			ArrayList<ConnectionWithTransfers> connection = new ArrayList<>();
			for (ConnectionWithTransfers train : trains)
				if (train.getNumberOfTransfers() == 0) connection.add(train);
			
			if (connection.isEmpty()) throw new NoRouteFoundException();
			Collections.sort(connection);
			ArrayList<ConnectionWithTransfers> connection2 = new ArrayList<>();
			int size = Math.min(5, connection.size());
			for (int i = 0; i < size; ++i)
				connection2.add(connection.get(i));
			
			return connection2;
		}
	}
	
	Station station1 = new Station("Kraków", LocalDateTime.now(), LocalDateTime.now());
	Station station2 = new Station("Warszawa", LocalDateTime.now(), LocalDateTime.now());
	Station station3 = new Station("Szczecin", LocalDateTime.now(), LocalDateTime.now());
	Station station4 = new Station("Koszalin", LocalDateTime.now(), LocalDateTime.now());
	Station station5 = new Station("Szczecinek", LocalDateTime.now(), LocalDateTime.now());
	
	
	DirectConnection dC1= new DirectConnection("TCS", 12, 21.37, TrainIsReservation.WITH_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station1, station2))));
	DirectConnection dC2 = new DirectConnection("UJ", 13, 12.34, TrainIsReservation.WITH_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station2, station3))));
	DirectConnection dC3 = new DirectConnection("pierdole To", 123, 34.56, TrainIsReservation.WITHOUT_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station1, station4, station2))));
	DirectConnection dC4 = new DirectConnection("AverageCompanyName", 13, 34.56, TrainIsReservation.WITHOUT_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station3, station5))));
	DirectConnection dC5 = new DirectConnection("ChujoweCompanyName", 34, 67.67, TrainIsReservation.WITHOUT_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station5, station2))));
	DirectConnection dC6 = new DirectConnection("XD", 67, 67.88, TrainIsReservation.WITHOUT_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station1, station3))));
	
	@Test
	public void BasicFunctionalityTest() {
		Finder finder = new Finder("Kraków", "Warszawa", LocalDateTime.now());
		finder.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6));
		List<ConnectionWithTransfers> connections = finder.getRoutes();

		for(ConnectionWithTransfers x : connections) {
			System.out.println("CONNECTION");
			for (DirectConnection s : x.getTrains()) {
				s.display();
				System.out.println();
			}
		}
	}
	
	@Test
	public void StationDoesNotExistsTest() {
		Finder finder2 = new Finder("Szczecin", "Skierniewice", LocalDateTime.now());
		finder2.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6));
		List<ConnectionWithTransfers> connections2 = finder2.getRoutes();
		assert(connections2.isEmpty());
	}
	
	@Test
	public void NothingFoundTest() {
		Finder finder = new Finder("Szczecin", "Kraków", LocalDateTime.now());
		finder.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6));
		assert(finder.getRoutes().isEmpty());
	}
	
	@Test
	public void RoutesSortedByCostTest() {
		Finder finder = new Finder("Kraków", "Warszawa", LocalDateTime.now());
		finder.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6));
		List<ConnectionWithTransfers> connections = finder.getCheapRoutes();
		System.out.println("DISPLAY <#");
		for(ConnectionWithTransfers c : connections)
			c.displayLess();
	}
	
	@Test
	public void RoutesWithoutTransfers() {
		Finder finder = new Finder("Kraków", "Warszawa", LocalDateTime.now());
		finder.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6));
		List<ConnectionWithTransfers> connection = finder.getRoutesWithoutTransfers();
		System.out.println("DISPLAY");
		for(ConnectionWithTransfers c : connection)
			c.displayLess();
	}
	
	@Test
	public void TimeDifferencesTest() {
		//CODE HERE
	}
	
}