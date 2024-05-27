import org.junit.Test;
import pl.tcs.oopproject.viewmodel.carriage.Carriage;
import pl.tcs.oopproject.viewmodel.carriage.CarriageClassType;
import pl.tcs.oopproject.viewmodel.carriage.CarriageType;
import pl.tcs.oopproject.viewmodel.seat.Seat;
import pl.tcs.oopproject.viewmodel.seat.SeatType;

import static org.junit.Assert.assertEquals;

public class CarriageTest {
	@Test
	public void carriageMethods() {
		Carriage carriage = new Carriage(CarriageClassType.SECOND_CLASS, CarriageType.COMPARTMENT_CARRIAGE, 56, 120);
		carriage.display();
		
		Carriage carriage1 = new Carriage(CarriageClassType.FIRST_CLASS, CarriageType.SINGLE_COMPARTMENT_CARRIAGE, 123, 23);
		carriage1.display();
	}
	
	@Test
	public void carriageSeats() {
		Seat s = new Seat(SeatType.BERTH, 1);
		Seat s1 = new Seat(SeatType.WINDOW, 2);
		Seat s2 = new Seat(SeatType.CORRIDOR, 34);
		
		Carriage carriage = new Carriage(CarriageClassType.FIRST_CLASS, CarriageType.SLEEPER, 23, 233);
		carriage.displaySeats();
		carriage.displaySeats();
		
	}
	
	
	
}
