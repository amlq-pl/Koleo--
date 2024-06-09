package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.UsersChecker;
import pl.tcs.oopproject.model.users.Person;

import java.sql.*;

public class Users implements UsersChecker {

    @Override
    public Person authenticate(String login, String password) throws SQLException {
        Person person = null;
        PreparedStatement preparedStatement = DB.connection.prepareStatement("select k.imie,k.nazwisko,k.data_urodzenia,k.email,k.nr_telefonu " +
                "from konto ko join klienci k on k.id_klienta=ko.id_klienta " +
                "where ko.login=? and ko.haslo=?");
        preparedStatement.setString(1, login);
        preparedStatement.setInt(2, password.hashCode());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            person = new Person(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDate(3).toLocalDate(),
                    resultSet.getString(4),
                    resultSet.getString(5));
        }
        return person;
    }

    @Override
    public boolean insert(Person p, String login, String password) throws SQLException {
        PreparedStatement ps;
        if (password == null) {
            ps = DB.connection.prepareStatement("insert into uzytkownicy(imie, nazwisko, data_urodzenia, email, nr_telefonu, login, haslo) values(?, ?, ?, ?, ?, ?, null);");
            ps.setString(1, p.getName());
            ps.setString(2, p.getSurname());
            ps.setDate(3, Date.valueOf(p.getDateOfBirth()));
            ps.setString(4, p.getEmailAddress());
            ps.setString(5, p.getTelephoneNumber());
            ps.setString(6, login);
        } else {
            ps = DB.connection.prepareStatement("insert into uzytkownicy(imie, nazwisko, data_urodzenia, email, nr_telefonu, login, haslo) values(?, ?, ?, ?, ?, ?, ?);");
            ps.setString(1, p.getName());
            ps.setString(2, p.getSurname());
            ps.setDate(3, Date.valueOf(p.getDateOfBirth()));
            ps.setString(4, p.getEmailAddress());
            ps.setString(5, p.getTelephoneNumber());
            ps.setString(6, login);
            ps.setInt(7, password.hashCode());
        }
        return ps.executeUpdate() == 1;
    }

    @Override
    public boolean checkIfUserExists(String login) throws SQLException {
        PreparedStatement statement = DB.connection.prepareStatement("select * from konto ko where ko.login=?");
        statement.setString(1, login);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    @Override
    public boolean checkIfPersonExists(Person person) throws SQLException {
        PreparedStatement statement = DB.connection.prepareStatement("select * from klienci k where k.email=?");
        statement.setString(1, person.getEmailAddress());
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
}
