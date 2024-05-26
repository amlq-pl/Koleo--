//import org.junit.Test;
//import pl.tcs.oopproject.viewmodel.connection.*;
//import pl.tcs.oopproject.viewmodel.station.Station;
//
//import java.time.LocalDateTime;
//import java.time.Month;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
//public class TrainTest {
//	@Test
//	public void test1() {
//
//		//FIRST CONNECTION
//		Station start = new Station("Szczecin", LocalDateTime.now(), LocalDateTime.now());
//		Station end = new Station("Warszawa", LocalDateTime.now(), LocalDateTime.now());
//		ArrayList<Station> list = new ArrayList<>();
//		list.add(start); list.add(end);
//		DirectConnection directConnection = new DirectConnection("PKP", 12, 21.37, TrainIsReservation.WITH_RESERVATION, new TrainConnection(list));
//		ArrayList<String> arrayList = new ArrayList<>(); arrayList.add(start.getTown());
//		ArrayList<DirectConnection> d = new ArrayList<>();
//		d.add(directConnection);
//		ConnectionWithTransfers temp = new ConnectionWithTransfers(start, end, d, arrayList);
//
//		//SECOND
//		Station start1 = new Station("Warszawa", LocalDateTime.now(), LocalDateTime.now());
//		Station end1 = new Station("Koszalin", LocalDateTime.now(), LocalDateTime.now());
//		ArrayList<Station> list1 = new ArrayList<>();
//		list.add(start); list.add(end1);
//		DirectConnection directConnection1 = new DirectConnection("PKP", 12, 21.37, TrainIsReservation.WITH_RESERVATION, new TrainConnection(list1));
//		ArrayList<String> arrayList1 = new ArrayList<>(); arrayList.add(start1.getTown());
//		ArrayList<DirectConnection> d1 = new ArrayList<>();
//		d1.add(directConnection1);
//		ConnectionWithTransfers temp1 = new ConnectionWithTransfers(start1, end1, d1, arrayList1);
//
//		//THIRD
//		Station start2 = new Station("Andrey", LocalDateTime.now(), LocalDateTime.now());
//		Station end2 = new Station("Kraków", LocalDateTime.now(), LocalDateTime.now());
//		ArrayList<Station> list2 = new ArrayList<>();
//		list2.add(start2); list2.add(end2);
//		DirectConnection directConnection2 = new DirectConnection("PKP", 12, 21.37, TrainIsReservation.WITH_RESERVATION, new TrainConnection(list2));
//		ArrayList<String> arrayList2 = new ArrayList<>(); arrayList2.add(start2.getTown());
//		ArrayList<DirectConnection> d2 = new ArrayList<>();
//		d2.add(directConnection2);
//		ConnectionWithTransfers temp2 = new ConnectionWithTransfers(start2, end2, d2, arrayList2);
//
//
//		ConnectionFinder connectionFinder = new ConnectionFinder("Szczecin", "Koszalin", LocalDateTime.of(2024, Month.MAY, 13, 12, 12));
//		connectionFinder.setTrains(List.of(directConnection, directConnection1, directConnection1));
//		try {
//			List<ConnectionWithTransfers> l =  connectionFinder.getRoutes();
//			for(ConnectionWithTransfers c : l)
//				c.display();
//		}
//		catch (Exception ignored) {}
//	}
//}
