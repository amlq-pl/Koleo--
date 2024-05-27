import org.junit.Test;
import pl.tcs.oopproject.viewmodel.connection.ConnectionWithTransfers;
import pl.tcs.oopproject.viewmodel.connection.DirectConnection;
import pl.tcs.oopproject.viewmodel.connection.TrainConnection;
import pl.tcs.oopproject.viewmodel.connection.TrainIsReservation;
import pl.tcs.oopproject.viewmodel.station.Station;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConnectionWithTransfersTest {
	
	@Test
	public void test1() {
		Station station1 = new Station("Kraków", LocalDateTime.now(), LocalDateTime.now());
		Station station2 = new Station("Warszawa", LocalDateTime.now(), LocalDateTime.now());
		Station station3 = new Station("Szczecin", LocalDateTime.now(), LocalDateTime.now());
		Station station4 = new Station("Koszalin", LocalDateTime.now(), LocalDateTime.now());
		Station station5 = new Station("Szczecinek", LocalDateTime.now(), LocalDateTime.now());
		
		DirectConnection dC11= new DirectConnection("TCS", 12, 21, TrainIsReservation.WITH_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station1, station2, station3, station4))));
		DirectConnection dC12 = new DirectConnection("TCS", 12, 21.10, TrainIsReservation.WITH_RESERVATION, new TrainConnection(new ArrayList<>(List.of(station3, station4, station5))));
		
		ConnectionWithTransfers connection = new ConnectionWithTransfers(station1, station5, new ArrayList<>(List.of(dC11, dC12)), new ArrayList<>(List.of(station4.getTown())));
		ArrayList<ArrayList<Station>> xd = connection.getRoute();
		
		for(ArrayList<Station> x : xd) {
			for(Station d : x) {
				d.display();
			}
			System.out.println();
		}
		
	}
	
}
