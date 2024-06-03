import org.junit.Test;
import pl.tcs.oopproject.model.connection.ConnectionWithTransfers;
import pl.tcs.oopproject.model.connection.DirectConnection;
import pl.tcs.oopproject.model.connection.TrainConnection;
import pl.tcs.oopproject.model.connection.TrainIsReservation;
import pl.tcs.oopproject.viewmodel.connection.*;
import pl.tcs.oopproject.model.exception.NoRouteFoundException;
import pl.tcs.oopproject.model.station.Station;

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
		
		private boolean contains(ArrayList<DirectConnection> stack) {
			System.out.println("CONTAINS " + trains.size());
			for(ConnectionWithTransfers connection : trains) {
				System.out.println(connection.getNumberOfTransfers() + 1  + " || " + stack.size());
				for(int i = 0; i < stack.size(); ++i) {
					System.out.println("STATION");
					for(Station s : stack.get(i).getStations())
						System.out.println(s);
				}
				if(connection.getNumberOfTransfers() + 1 != stack.size()) continue;
				boolean equal = true;
				System.out.println("NEW TRACE " + stack.size());
				for(int i = 0; i < stack.size() ; ++i){
							//System.out.println(stack.get(i) + " + " + connection.getTrains().get(i));
					if(!stack.get(i).equals( connection.getTrains().get(i))){
						equal = false;
						break;
					} //EQUALS DOES NOT WORK!!!!
				}
				if(equal) return true;
			}
			System.out.println("FALSE");
			return false;
		}
		
		private void findConnection(ArrayList<DirectConnection> allTrains, String temp, ArrayList<DirectConnection> stack, ArrayList<String> transfersStack, HashSet<DirectConnection> visitedConnections, HashSet<String> visitedStations) {
			for (int i = 0; i < allTrains.size(); ++i) {
				DirectConnection tempConnection = allTrains.get(i);
				if (tempConnection.contains(temp) && !visitedConnections.contains(tempConnection)) {
					if(!stack.isEmpty()) {
						LocalDateTime departureTime = stack.get(stack.size() - 1).getStation(temp).departureTime();
						if (tempConnection.getStation(temp).arrivalTime().isBefore(departureTime))
							continue;
					}
					stack.add(tempConnection);
					visitedConnections.add(tempConnection);
					transfersStack.add(temp);
					if (tempConnection.contains(stationB) && tempConnection.getIndexOfStation(stationB) > tempConnection.getIndexOfStation(temp)) {
						Station sA = new Station(stationA,
								stack.get(0).getStation(stationA).departureTime(),
								stack.get(0).getStation(stationA).arrivalTime());
						Station sB = new Station(stationB,
								stack.get(stack.size() - 1).getStation(stationB).departureTime(),
								stack.get(stack.size() - 1).getStation(stationB).arrivalTime());
						if(!contains(stack))
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
						if (!visitedStations.contains(tempConnection.getStationAt(j).town())) {
							visitedStations.add(tempConnection.getStationAt(j).town());
							findConnection(allTrains, tempConnection.getStationAt(j).town(), stack, transfersStack, visitedConnections, visitedStations);
						} else break;
					}
					
					for (int j = index + 1; j < tempConnection.getSize(); ++j) {
						visitedStations.remove(tempConnection.getStationAt(j).town());
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
		public List<ConnectionWithTransfers> getCheapRoutes() {
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
		public List<ConnectionWithTransfers> getRoutesWithoutTransfers(){
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
	Station station6 = new Station("Kędzierzyn Koźle", LocalDateTime.of(2024, 5, 27, 4, 20), LocalDateTime.of(2024, 5, 27, 4, 22));
	Station station7 = new Station("Katowice", LocalDateTime.of(2024, 5, 27, 4, 44), LocalDateTime.of(2024, 5, 27, 4, 48));
	Station station8 = new Station("Katowice", LocalDateTime.of(2024, 5, 27, 3, 44), LocalDateTime.of(2024, 5, 27, 3, 48));
	Station station9 = new Station("Jastrzębia Góra", LocalDateTime.of(2024, 5, 27, 3, 50), LocalDateTime.of(2024, 5, 27, 4, 55));
	Station station10 = new Station("Katowice", LocalDateTime.of(2024, 5, 27, 7, 44), LocalDateTime.of(2024, 5, 27, 7, 48));
	Station station11 = new Station("Jastrzębia Góra", LocalDateTime.of(2024, 5, 27, 8, 50), LocalDateTime.of(2024, 5, 27, 8, 55));
	
	
	DirectConnection dC11= new DirectConnection("TCS", 12, 21, TrainIsReservation.WITH_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station1, station2, station3, station4))));
	DirectConnection dC12 = new DirectConnection("TCS", 12, 21.10, TrainIsReservation.WITH_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station3, station4, station5))));
	DirectConnection dC1= new DirectConnection("TCS", 12, 21.1, TrainIsReservation.WITH_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station1, station2))));
	DirectConnection dC2 = new DirectConnection("UJ", 13, 12.1, TrainIsReservation.WITH_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station2, station3))));
	DirectConnection dC3 = new DirectConnection("pierdole To", 123, 34.56, TrainIsReservation.WITHOUT_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station1, station4, station2))));
	DirectConnection dC4 = new DirectConnection("AverageCompanyName", 13, 34.56, TrainIsReservation.WITHOUT_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station3, station5))));
	DirectConnection dC5 = new DirectConnection("ChujoweCompanyName", 34, 67.67, TrainIsReservation.WITHOUT_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station5, station2))));
	DirectConnection dC6 = new DirectConnection("XD", 67, 67.88, TrainIsReservation.WITHOUT_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station1, station3))));
	DirectConnection dC7 = new DirectConnection("TroubleSomeCompanyName", 34, 67.89, TrainIsReservation.WITHOUT_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station6, station7))));
	DirectConnection dC8 = new DirectConnection("TroubleSomeCompanyName", 5667, 789.88, TrainIsReservation.WITHOUT_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station8, station9))));
	DirectConnection dC9 = new DirectConnection("TroubleSomeCompanyName", 5667, 789.88, TrainIsReservation.WITHOUT_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station10, station11))));
	DirectConnection dC10 = new DirectConnection("XDcompanyName", 457, 78.08, TrainIsReservation.WITHOUT_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station10, station11, station4, station5))));
	
	
	public void fillFinder(Finder finder) {
		finder.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6, dC7, dC8, dC9, dC10, dC11, dC12));
	}

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
	public void TimeDifferencesThatDoesNotWorkTest() {
		Finder finder = new Finder("Kędzierzyn Koźle", "Jastrzębia Góra", LocalDateTime.now());
		finder.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6, dC7, dC8));
		List<ConnectionWithTransfers> connection = finder.getRoutes();
		assert connection.isEmpty();
	}
	
	@Test
	public void TestTimeDifferencesThatWorkTest() {
		Finder finder = new Finder("Kędzierzyn Koźle", "Jastrzębia Góra", LocalDateTime.now());
		finder.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6, dC7, dC8, dC9));
		List<ConnectionWithTransfers> connection = finder.getRoutes();
		assert !connection.isEmpty();
		
		System.out.println("DISPLAY");
		for(ConnectionWithTransfers c : connection)
			c.display();
	}
	
	@Test
	public void NestStations() {
		Finder finder = new Finder("Jastrzębia Góra", "Warszawa", LocalDateTime.now());
		fillFinder(finder);
		List<ConnectionWithTransfers> connection = finder.getRoutes();
		assert !connection.isEmpty();
		
		System.out.println("DISPLAY");
		for(ConnectionWithTransfers c : connection) {
			System.out.println("CONNECTION");
			c.display();
			System.out.println("STATIONS");
			for(Station s : c.getStations())
				System.out.println(s);
		}
	}
	
	@Test
	public void ParallelTracksTest() {
		Finder finder = new Finder("Warszawa", "Szczecinek", LocalDateTime.now());
		finder.setTrains(List.of(dC11, dC12));
		List<ConnectionWithTransfers> connection = finder.getRoutes();
		assert !connection.isEmpty();
		assert connection.size() == 1;
		
		System.out.println("DISPLAY");
		for(ConnectionWithTransfers c : connection) {
			System.out.println("CONNECTION");
			c.display();
			System.out.println("STATIONS");
			for(Station s : c.getStations())
				System.out.println(s);
		}
	}
	
	@Test
	public void TracksTest() {
		Finder finder = new Finder("Warszawa", "Szczecinek", LocalDateTime.now());
		fillFinder(finder);
		List<ConnectionWithTransfers> connection = finder.getRoutes();
		assert !connection.isEmpty();
		System.out.println(connection.size());
		
		System.out.println("-----------------------------------------------------------------------------------------------");
		for(ConnectionWithTransfers c : connection) {
			System.out.println(c.getNumberOfTransfers());
			System.out.println("CONNECTION");
			c.display();
			System.out.println("STATIONS");
			for(Station s : c.getStations())
				System.out.println(s);
		}
	}
	
}