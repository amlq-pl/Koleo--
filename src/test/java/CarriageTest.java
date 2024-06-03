import org.junit.Test;
import pl.tcs.oopproject.model.carriage.Carriage;
import pl.tcs.oopproject.model.carriage.CarriageClass;
import pl.tcs.oopproject.model.carriage.CarriageType;
import pl.tcs.oopproject.model.seat.Seat;
import pl.tcs.oopproject.model.seat.SeatType;

public class CarriageTest {
	@Test
	public void carriageMethods() {
		Carriage carriage = new Carriage(CarriageClass.SECOND_CLASS, CarriageType.COMPARTMENT_CARRIAGE, 56, 120);
		carriage.display();
		
		Carriage carriage1 = new Carriage(CarriageClass.FIRST_CLASS, CarriageType.SINGLE_COMPARTMENT_CARRIAGE, 123, 23);
		carriage1.display();
	}
	
	@Test
	public void carriageSeats() {
		Seat s = new Seat(SeatType.BERTH, 1);
		Seat s1 = new Seat(SeatType.WINDOW, 2);
		Seat s2 = new Seat(SeatType.CORRIDOR, 34);
		
		Carriage carriage = new Carriage(CarriageClass.FIRST_CLASS, CarriageType.SLEEPER, 23, 233);
	}
}
