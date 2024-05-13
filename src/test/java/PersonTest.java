import org.junit.Test;
import pl.tcs.oopproject.viewmodel.exception.KoleoException;
import pl.tcs.oopproject.viewmodel.users.Person;
import pl.tcs.oopproject.viewmodel.users.PersonFactory;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class PersonTest {
	@Test
	public void dateAndPhoneNumberTest() {
		Person A = new Person("Aaaa", "Bbbb", LocalDate.of(2000, 12, 13), "sfnio234f@student.uj.edu.pl", null);
		A.display();
	}
}
