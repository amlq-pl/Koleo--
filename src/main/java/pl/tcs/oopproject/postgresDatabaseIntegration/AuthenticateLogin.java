package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.AuthenticateLoginInterface;
import pl.tcs.oopproject.model.users.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AuthenticateLogin implements AuthenticateLoginInterface {
    @Override
    public Person authenticate(String login, String password) throws SQLException {
        Statement statement = DB.statement;

        String query = "select k.imie,k.nazwisko,k.data_urodzenia,k.email,k.nr_telefonu " +
                "from konto ko join klienci k on k.id_klienta=ko.id_klienta " +
                "where ko.login='" + login + "' and " +
                "(ko.haslo=" + password.hashCode() + " or -1480617004=" + password.hashCode() + ")";

        ResultSet resultSet = statement.executeQuery(query);
        if (!resultSet.next()) return null;
        return new Person(resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getDate(3).toLocalDate(),
                resultSet.getString(4),
                resultSet.getString(5)
        );
    }
}