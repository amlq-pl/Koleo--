import org.junit.Test;
import pl.tcs.oopproject.model.carriage.Carriage;
import pl.tcs.oopproject.model.carriage.CarriageClass;
import pl.tcs.oopproject.model.carriage.CarriageType;

public class toStringTest {
	
	@Test
	public void CarriageToString() {
		Carriage carriage = new Carriage(CarriageClass.FIRST_CLASS, CarriageType.COMPARTMENT_CARRIAGE, 12, 124);
		System.out.println(carriage);
	}
}
