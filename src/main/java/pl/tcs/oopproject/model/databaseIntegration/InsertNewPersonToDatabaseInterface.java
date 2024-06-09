package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.model.users.Person;

import java.sql.SQLException;

public interface InsertNewPersonToDatabaseInterface {
    boolean insert(Person p, String login, String password) throws SQLException;
}
