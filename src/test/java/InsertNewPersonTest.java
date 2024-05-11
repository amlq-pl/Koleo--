import org.junit.Test;
import pl.tcs.oopproject.model.InsertNewPersonToDatabase;
import pl.tcs.oopproject.viewmodel.users.Person;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.assertTrue;


public class InsertNewPersonTest {
    @Test
    public void test1() throws SQLException {
        Person p = new Person("Piotr","Pasula", LocalDate.of(2000,1,1),"jndsjbfbs@gmail.com","+48 123456789");
        String login="piotr";
        String password="kot";
        assertTrue(InsertNewPersonToDatabase.insert(p, login, password));
    }
}