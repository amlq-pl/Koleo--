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
        String query="select k.imie,k.nazwisko,k.data_urodzenia,k.email,k.nr_telefonu "+
                "from konto ko join klienci k on k.id_klienta=ko.id_klienta "+
                "where ko.login=\'"+login+"\' and "+
                "(ko.haslo="+ password.hashCode() + " or -1480617004="+ password.hashCode() +")";
        System.out.println(query);
        Person p= AuthenticateLogin.authenticate(login,password);
        assertEquals(p.getName(),"Jan");
        assertEquals(p.getSurname(),"Nowicki");
        assertEquals(p.getEmailAddress(),"jannowicki@gazeta.pl");
    }



}