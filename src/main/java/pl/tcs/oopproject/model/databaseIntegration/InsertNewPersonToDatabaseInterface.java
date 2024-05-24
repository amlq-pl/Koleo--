package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.viewmodel.users.Person;

import java.sql.SQLException;

public interface InsertNewPersonToDatabaseInterface {
    boolean insertWithoutAccount(Person p) throws SQLException;
    boolean insert( Person p, String login, String password) throws SQLException;
}
