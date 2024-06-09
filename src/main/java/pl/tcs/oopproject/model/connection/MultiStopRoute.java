package pl.tcs.oopproject.model.connection;

import org.jetbrains.annotations.NotNull;
import pl.tcs.oopproject.model.discount.PricePLN;
import pl.tcs.oopproject.model.exception.KoleoException;
import pl.tcs.oopproject.model.exception.NoRouteFoundException;
import pl.tcs.oopproject.model.history.HistoryLongTermTicket;
import pl.tcs.oopproject.model.station.Station;
import pl.tcs.oopproject.postgresDatabaseIntegration.TicketGet;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MultiStopRoute implements RouteElement, TransferableRoute, Comparable<MultiStopRoute> {
	private final Station stationA;
	private final Station stationB;
	private final ArrayList<ScheduledTrain> trains; //trains that general connection consists of
	private final ArrayList<String> transferStations; //stations when customer should get into
	
	public MultiStopRoute(Station A, Station B, ArrayList<ScheduledTrain> scheduledTrains, ArrayList<String> transferStations) {
		this.trains = scheduledTrains;
		this.stationA = A;
		this.stationB = B;
		this.transferStations = transferStations;
	}
	
	public ArrayList<ArrayList<Station>> route() {
		ArrayList<ArrayList<Station>> list = new ArrayList<>();
		transferStations.add(stationB.town());
		for (int i = 0; i < trains.size(); ++i) {
			int j = trains.get(i).getIndexOfStation(transferStations.get(i));
			int k = trains.get(i).getIndexOfStation(transferStations.get(i + 1));
			list.add(new ArrayList<>());
			for (int t = j; t <= k; ++t) {
				list.get(i).add(trains.get(i).getStation(t));
			}
		}
		
		transferStations.remove(stationB.town());
		
		return list;
	}
	
	
	public List<ScheduledTrain> trains() {
		
		return trains;
	}
	
	public ReservationType trainType() {
		if (trains.size() != 1) return null;
		else return trains.get(0).getTrainType();
	}
	
	@Override
	public Station originStation() {
		return stationA;
	}
	
	@Override
	public Station destinationStation() {
		return stationB;
	}
	
	@Override
	public Station getStation(int index) throws IndexOutOfBoundsException {
		if (trains.isEmpty()) throw new NoRouteFoundException();
		int maxSize;
		int c;
		for (int i = 0; i < trains.size(); ++i) {
			maxSize = trains.get(i).numberOfStops() - trains.get(i).getIndexOfStation(transferStations.get(i));
			c = trains.get(i).getIndexOfStation(transferStations.get(i));
			if (maxSize <= index) return trains.get(i).getStation(c + index);
			index -= maxSize;
		} //TO CHECK IT!!!
		
		
		throw new IndexOutOfBoundsException();
	}
	
	
	public List<Station> stations() {
		ArrayList<Station> list = new ArrayList<>();
		
		int size = trains.size();
		
		for (int i = 0; i < size - 1; ++i) {
			int j = trains.get(i).getIndexOfStation(transferStations.get(i));
			int k = trains.get(i).getIndexOfStation(transferStations.get(i + 1));
			for (int t = j; t <= k; ++t)
				list.add(trains.get(i).getStation(t));
		}
		
		int j = trains.get(size - 1).getIndexOfStation(transferStations.get(size - 1));
		int k = trains.get(size - 1).getIndexOfStation(stationB.town());
		for (int t = j; t <= k; ++t)
			list.add(trains.get(size - 1).getStation(t));
		
		return list;
	}
	
	@Override
	public int numberOfTransfers() {
		return trains.size() - 1;
	}
	
	@Override
	public LocalDateTime departureTime() {
		return stationA.departureTime();
	}
	
	@Override
	public LocalDateTime arrivalTime() {
		return stationB.arrivalTime();
	}
	
	@Override
	public List<String> companies() {
		List<String> list = new ArrayList<>();
		for (ScheduledTrain t : trains)
			list.add(t.getCompany());
		return list;
	}
	
	@Override
	public List<Station> transferStations() {
		if (transferStations.isEmpty()) throw new NoRouteFoundException();
		List<Station> stations = new ArrayList<>();
		
		for (int i = 1; i < transferStations.size(); ++i) {
			int index = trains.get(i).getIndexOfStation(transferStations.get(i));
			stations.add(trains.get(i).getStation(index));
		}
		return stations;
	}
	
	
	@Override
	public PricePLN cost() throws SQLException {
		TicketGet ticketGet = new TicketGet();
		ArrayList<String> companies = new ArrayList<>();
		if(ActiveUser.getActiveUser() != null) {
			ArrayList<HistoryLongTermTicket> longTermTickets = ticketGet.getLongTermTickets(ActiveUser.getActiveUser());
			for (HistoryLongTermTicket t : longTermTickets) {
				if (t.isActive() && !companies.contains(t.longTermTicketType().company()))
					companies.add(t.longTermTicketType().company());
			}
		}
		
		double cost = 0;
		
		int size = trains.size();
		
		transferStations.add(stationB.town());
		
		for (int i = 0; i < size; ++i) {
			if(!companies.contains(trains.get(i).getCompany())) {
				double cost1 = trains.get(i).getCost().value();
				int j = trains.get(i).getIndexOfStation(transferStations.get(i));
				int k = trains.get(i).getIndexOfStation(transferStations.get(i + 1));
				cost += cost1 * (k - j + 1) / trains.get(i).numberOfStops();
			}
		}
		
		transferStations.remove(stationB.town());
		return new PricePLN((double) Math.round(cost * 100) / 100);
	}
	
	@Override
	public int numberOfStops() {
		return this.stations().size();
	}
	
	@Override
	public int compareTo(@NotNull MultiStopRoute o) {
		if(!arrivalTime().isEqual(o.arrivalTime()))
			return arrivalTime().isBefore(o.arrivalTime()) ? -1 : 1;
		return departureTime().compareTo(o.departureTime());
	}
	
	public String toString() {
		try {
			return "Departure Station: " + originStation().town() + " " + originStation().departureTime() + "\n" +
					"Arrival Station: " + destinationStation().town() + " " + destinationStation().arrivalTime() + "\n" +
					"Cost: " + cost().toString();
		} catch (SQLException e) {
			throw new KoleoException();
		}
	}
	
	public void display() {
		for (ScheduledTrain d : trains)
			System.out.println(d);
	}
}
