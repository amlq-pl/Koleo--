import org.junit.Test;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.connection.ScheduledTrain;
import pl.tcs.oopproject.model.connection.RouteStops;
import pl.tcs.oopproject.model.connection.ReservationType;
import pl.tcs.oopproject.model.station.Station;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MultiStopRouteTest {
	
	@Test
	public void test1() {
		Station station1 = new Station("Kraków", LocalDateTime.now(), LocalDateTime.now());
		Station station2 = new Station("Warszawa", LocalDateTime.now(), LocalDateTime.now());
		Station station3 = new Station("Szczecin", LocalDateTime.now(), LocalDateTime.now());
		Station station4 = new Station("Koszalin", LocalDateTime.now(), LocalDateTime.now());
		Station station5 = new Station("Szczecinek", LocalDateTime.now(), LocalDateTime.now());
		
		ScheduledTrain dC11= new ScheduledTrain("TCS", 12, 21, ReservationType.WITH_RESERVATION, new RouteStops(new ArrayList<>(List.of(station1, station2, station3, station4))));
		ScheduledTrain dC12 = new ScheduledTrain("TCS", 12, 21.10, ReservationType.WITH_RESERVATION, new RouteStops(new ArrayList<>(List.of(station3, station4, station5))));
		
		MultiStopRoute connection = new MultiStopRoute(station1, station5, new ArrayList<>(List.of(dC11, dC12)), new ArrayList<>(List.of(station4.town())));
//		ArrayList<ArrayList<Station>> xd = connection.getRoute();
//
//		for(ArrayList<Station> x : xd) {
//			for(Station d : x) {
//				System.out.println(x);
//			}
//			System.out.println();
//		}
		
	}
	
}
