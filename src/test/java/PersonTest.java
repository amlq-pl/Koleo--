import org.junit.Test;
import pl.tcs.oopproject.model.users.Person;

import java.time.LocalDate;

public class PersonTest {
	@Test
	public void dateAndPhoneNumberTest() {
		Person A = new Person("Aaaa", "Bbbb", LocalDate.of(2000, 12, 13), "sfnio234f@student.uj.edu.pl", null);
		A.display();
	}
}
