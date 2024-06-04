import org.junit.Test;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.connection.ScheduledTrain;
import pl.tcs.oopproject.model.connection.RouteStops;
import pl.tcs.oopproject.model.connection.ReservationType;
import pl.tcs.oopproject.viewmodel.connection.*;
import pl.tcs.oopproject.model.exception.NoRouteFoundException;
import pl.tcs.oopproject.model.station.Station;

import java.time.LocalDateTime;
import java.util.*;

public class TrainTest {
	
	public class Finder implements FindTrainConnection {
		private static final int maxTransferNumber = 4;
		private static final int hours = 8;
		private final ArrayList<MultiStopRoute> trains = new ArrayList<>();
		private final String stationA;
		private final String stationB;
		private final LocalDateTime departureDate;
		
		public Finder(String stationA, String stationB, LocalDateTime departureDate) {
			this.stationA = stationA;
			this.stationB = stationB;
			this.departureDate = departureDate;
		}
		
		private boolean contains(ArrayList<ScheduledTrain> stack) {
			System.out.println("CONTAINS " + trains.size());
			for(MultiStopRoute connection : trains) {
				System.out.println(connection.numberOfTransfers() + 1  + " || " + stack.size());
				for(int i = 0; i < stack.size(); ++i) {
					System.out.println("STATION");
					for(Station s : stack.get(i).stations())
						System.out.println(s);
				}
				if(connection.numberOfTransfers() + 1 != stack.size()) continue;
				boolean equal = true;
				System.out.println("NEW TRACE " + stack.size());
				for(int i = 0; i < stack.size() ; ++i){
							//System.out.println(stack.get(i) + " + " + connection.trains().get(i));
					if(!stack.get(i).equals( connection.trains().get(i))){
						equal = false;
						break;
					} //EQUALS DOES NOT WORK!!!!
				}
				if(equal) return true;
			}
			System.out.println("FALSE");
			return false;
		}
		
		private void findConnection(ArrayList<ScheduledTrain> allTrains, String temp, ArrayList<ScheduledTrain> stack, ArrayList<String> transfersStack, HashSet<ScheduledTrain> visitedConnections, HashSet<String> visitedStations) {
			for (int i = 0; i < allTrains.size(); ++i) {
				ScheduledTrain tempConnection = allTrains.get(i);
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
							trains.add(new MultiStopRoute(sA, sB, new ArrayList<>(stack), new ArrayList<>(transfersStack)));
						
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
		
		
		public void setTrains(List<ScheduledTrain> l) {
			ArrayList<ScheduledTrain> allTrains = new ArrayList<>(l);
			ArrayList<ScheduledTrain> stack = new ArrayList<>();
			ArrayList<String> transferStack = new ArrayList<>();
			HashSet<ScheduledTrain> visitedScheduledTrain = new HashSet<>();
			HashSet<String> visitedStation = new HashSet<>();
			visitedStation.add(stationA);
			findConnection(allTrains, stationA, stack, transferStack, visitedScheduledTrain, visitedStation);
		}
		
		@Override
		public List<MultiStopRoute> findTrainRoutes() {
			Collections.sort(trains);
			ArrayList<MultiStopRoute> connections = new ArrayList<>();
			int size = Math.min(5, trains.size());
			for (int i = 0; i < size; ++i)
				connections.add(trains.get(i));
			
			return connections;
		}
		
		@Override
		public List<MultiStopRoute> findCheapestTrainRoutes() {
			ArrayList<MultiStopRoute> connections = new ArrayList<>();
			int size = Math.min(5, trains.size());
			for (int i = 0; i < size; ++i) {
				for (int j = i + 1; j < trains.size(); ++j) {
					if (trains.get(i).cost().value() > trains.get(j).cost().value()) {
						Collections.swap(trains, i, j);
					}
				}
			}
			
			for (int i = 0; i < size; ++i)
				connections.add(trains.get(i));
			
			return connections;
		}
		
		@Override
		public List<MultiStopRoute> getDirectTrainRoutes(){
			ArrayList<MultiStopRoute> connection = new ArrayList<>();
			for (MultiStopRoute train : trains)
				if (train.numberOfTransfers() == 0) connection.add(train);
			
			if (connection.isEmpty()) throw new NoRouteFoundException();
			Collections.sort(connection);
			ArrayList<MultiStopRoute> connection2 = new ArrayList<>();
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
	
	
	ScheduledTrain dC11= new ScheduledTrain("TCS", 12, 21, ReservationType.WITH_RESERVATION, new RouteStops(new ArrayList<>(List.of(station1, station2, station3, station4))));
	ScheduledTrain dC12 = new ScheduledTrain("TCS", 12, 21.10, ReservationType.WITH_RESERVATION, new RouteStops(new ArrayList<>(List.of(station3, station4, station5))));
	ScheduledTrain dC1= new ScheduledTrain("TCS", 12, 21.1, ReservationType.WITH_RESERVATION, new RouteStops(new ArrayList<>(List.of(station1, station2))));
	ScheduledTrain dC2 = new ScheduledTrain("UJ", 13, 12.1, ReservationType.WITH_RESERVATION, new RouteStops(new ArrayList<>(List.of(station2, station3))));
	ScheduledTrain dC3 = new ScheduledTrain("pierdole To", 123, 34.56, ReservationType.WITHOUT_RESERVATION, new RouteStops(new ArrayList<>(List.of(station1, station4, station2))));
	ScheduledTrain dC4 = new ScheduledTrain("AverageCompanyName", 13, 34.56, ReservationType.WITHOUT_RESERVATION, new RouteStops(new ArrayList<>(List.of(station3, station5))));
	ScheduledTrain dC5 = new ScheduledTrain("ChujoweCompanyName", 34, 67.67, ReservationType.WITHOUT_RESERVATION, new RouteStops(new ArrayList<>(List.of(station5, station2))));
	ScheduledTrain dC6 = new ScheduledTrain("XD", 67, 67.88, ReservationType.WITHOUT_RESERVATION, new RouteStops(new ArrayList<>(List.of(station1, station3))));
	ScheduledTrain dC7 = new ScheduledTrain("TroubleSomeCompanyName", 34, 67.89, ReservationType.WITHOUT_RESERVATION, new RouteStops(new ArrayList<>(List.of(station6, station7))));
	ScheduledTrain dC8 = new ScheduledTrain("TroubleSomeCompanyName", 5667, 789.88, ReservationType.WITHOUT_RESERVATION, new RouteStops(new ArrayList<>(List.of(station8, station9))));
	ScheduledTrain dC9 = new ScheduledTrain("TroubleSomeCompanyName", 5667, 789.88, ReservationType.WITHOUT_RESERVATION, new RouteStops(new ArrayList<>(List.of(station10, station11))));
	ScheduledTrain dC10 = new ScheduledTrain("XDcompanyName", 457, 78.08, ReservationType.WITHOUT_RESERVATION, new RouteStops(new ArrayList<>(List.of(station10, station11, station4, station5))));
	
	
	public void fillFinder(Finder finder) {
		finder.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6, dC7, dC8, dC9, dC10, dC11, dC12));
	}

	@Test
	public void BasicFunctionalityTest() {
		Finder finder = new Finder("Kraków", "Warszawa", LocalDateTime.now());
		finder.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6));
		List<MultiStopRoute> connections = finder.findTrainRoutes();

		for(MultiStopRoute x : connections) {
			System.out.println("CONNECTION");
			for (ScheduledTrain s : x.trains()) {
				s.display();
				System.out.println();
			}
		}
	}
	
	@Test
	public void StationDoesNotExistsTest() {
		Finder finder2 = new Finder("Szczecin", "Skierniewice", LocalDateTime.now());
		finder2.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6));
		List<MultiStopRoute> connections2 = finder2.findTrainRoutes();
		assert(connections2.isEmpty());
	}
	
	@Test
	public void NothingFoundTest() {
		Finder finder = new Finder("Szczecin", "Kraków", LocalDateTime.now());
		finder.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6));
		assert(finder.findTrainRoutes().isEmpty());
	}
	
	@Test
	public void RoutesSortedByCostTest() {
		Finder finder = new Finder("Kraków", "Warszawa", LocalDateTime.now());
		finder.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6));
		List<MultiStopRoute> connections = finder.findCheapestTrainRoutes();
		System.out.println("DISPLAY <#");
		for(MultiStopRoute c : connections)
			c.displayLess();
	}
	
	@Test
	public void RoutesWithoutTransfers() {
		Finder finder = new Finder("Kraków", "Warszawa", LocalDateTime.now());
		finder.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6));
		List<MultiStopRoute> connection = finder.getDirectTrainRoutes();
		System.out.println("DISPLAY");
		for(MultiStopRoute c : connection)
			c.displayLess();
	}
	
	@Test
	public void TimeDifferencesThatDoesNotWorkTest() {
		Finder finder = new Finder("Kędzierzyn Koźle", "Jastrzębia Góra", LocalDateTime.now());
		finder.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6, dC7, dC8));
		List<MultiStopRoute> connection = finder.findTrainRoutes();
		assert connection.isEmpty();
	}
	
	@Test
	public void TestTimeDifferencesThatWorkTest() {
		Finder finder = new Finder("Kędzierzyn Koźle", "Jastrzębia Góra", LocalDateTime.now());
		finder.setTrains(List.of(dC1, dC2, dC3, dC4, dC5, dC6, dC7, dC8, dC9));
		List<MultiStopRoute> connection = finder.findTrainRoutes();
		assert !connection.isEmpty();
		
		System.out.println("DISPLAY");
		for(MultiStopRoute c : connection)
			c.display();
	}
	
	@Test
	public void NestStations() {
		Finder finder = new Finder("Jastrzębia Góra", "Warszawa", LocalDateTime.now());
		fillFinder(finder);
		List<MultiStopRoute> connection = finder.findTrainRoutes();
		assert !connection.isEmpty();
		
		System.out.println("DISPLAY");
		for(MultiStopRoute c : connection) {
			System.out.println("CONNECTION");
			c.display();
			System.out.println("STATIONS");
			for(Station s : c.stations())
				System.out.println(s);
		}
	}
	
	@Test
	public void ParallelTracksTest() {
		Finder finder = new Finder("Warszawa", "Szczecinek", LocalDateTime.now());
		finder.setTrains(List.of(dC11, dC12));
		List<MultiStopRoute> connection = finder.findTrainRoutes();
		assert !connection.isEmpty();
		assert connection.size() == 1;
		
		System.out.println("DISPLAY");
		for(MultiStopRoute c : connection) {
			System.out.println("CONNECTION");
			c.display();
			System.out.println("STATIONS");
			for(Station s : c.stations())
				System.out.println(s);
		}
	}
	
	@Test
	public void TracksTest() {
		Finder finder = new Finder("Warszawa", "Szczecinek", LocalDateTime.now());
		fillFinder(finder);
		List<MultiStopRoute> connection = finder.findTrainRoutes();
		assert !connection.isEmpty();
		System.out.println(connection.size());
		
		System.out.println("-----------------------------------------------------------------------------------------------");
		for(MultiStopRoute c : connection) {
			System.out.println(c.numberOfTransfers());
			System.out.println("CONNECTION");
			c.display();
			System.out.println("STATIONS");
			for(Station s : c.stations())
				System.out.println(s);
		}
	}
	
}