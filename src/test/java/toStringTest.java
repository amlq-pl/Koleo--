import org.junit.Test;
import pl.tcs.oopproject.model.assignedSeat.AssignedSeat;
import pl.tcs.oopproject.model.carriage.Carriage;
import pl.tcs.oopproject.model.carriage.CarriageClass;
import pl.tcs.oopproject.model.carriage.CarriageType;
import pl.tcs.oopproject.model.seat.Seat;
import pl.tcs.oopproject.model.seat.SeatType;

public class toStringTest {
	
	@Test
	public void CarriageToString() {
		Carriage carriage = new Carriage(CarriageClass.FIRST_CLASS, CarriageType.COMPARTMENT_CARRIAGE, 12, 124);
		System.out.println(carriage);
	}
	
	@Test
	public void SeatToString() {
		Seat seat = new Seat(SeatType.WINDOW, 12);
		System.out.println(seat);
	}
	
	@Test
	public void AssignedSeat(){
		Carriage carriage = new Carriage(CarriageClass.FIRST_CLASS, CarriageType.COMPARTMENT_CARRIAGE, 12, 124);
		Seat seat = new Seat(SeatType.WINDOW, 12);
		AssignedSeat as = new AssignedSeat(carriage, seat);
		System.out.println(as);
	}
	
}
