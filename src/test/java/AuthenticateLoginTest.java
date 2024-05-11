import org.junit.Test;
import pl.tcs.oopproject.model.AuthenticateLogin;
import pl.tcs.oopproject.viewmodel.users.Person;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;


public class AuthenticateLoginTest {
    @Test
    public void test1() throws SQLException {
        String login="iTppbdqen";
        String password="koleosuperuser";
        Person p= AuthenticateLogin.authenticate(login,password);
        assertEquals(p.getName(),"Jan");
        assertEquals(p.getSurname(),"Nowicki");
        assertEquals(p.getEmailAddress(),"jannowicki@gazeta.pl");
    }



}