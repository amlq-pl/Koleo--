package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.InsertNewPersonToDatabaseInterface;
import pl.tcs.oopproject.model.users.Person;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertNewPersonToDatabase implements InsertNewPersonToDatabaseInterface {

    @Override
    public boolean insert(Person p, String login, String password) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("insert into uzytkownicy(imie, nazwisko, data_urodzenia, email, nr_telefonu, login, haslo) values(?, ?, ?, ?, ?, ?, ?);");
        ps.setString(1, p.getName());
        ps.setString(2, p.getSurname());
        ps.setString(3, p.getDateOfBirth().toString());
        ps.setString(4, p.getEmailAddress());
        ps.setString(5, p.getTelephoneNumber());
        ps.setString(6, login);
        ps.setInt(7, password.hashCode());
        return ps.executeUpdate() == 1;
    }
}
