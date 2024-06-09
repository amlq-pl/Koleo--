package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.InsertNewPersonToDatabaseInterface;
import pl.tcs.oopproject.model.users.Person;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertNewPersonToDatabase implements InsertNewPersonToDatabaseInterface {

    @Override
    public boolean insert(Person p, String login, String password) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("insert into uzytkownicy(imie, nazwisko, data_urodzenia, email, nr_telefonu, login, haslo) values(?, ?, ?, ?, ?, ?, ?);");
        ps.setString(1, p.getName());
        ps.setString(2, p.getSurname());
        ps.setDate(3, Date.valueOf(p.getDateOfBirth()));
        ps.setString(4, p.getEmailAddress());
        ps.setString(5, p.getTelephoneNumber());
        ps.setString(6, login);
        if (password != null) {
            ps.setInt(7, password.hashCode());
        } else {
            ps.setString(7, null);
        }
        return ps.executeUpdate() == 1;
    }
}
