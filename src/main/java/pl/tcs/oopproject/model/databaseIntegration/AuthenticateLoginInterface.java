package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.viewmodel.users.Person;

import java.sql.SQLException;

public interface AuthenticateLoginInterface {
    Person authenticate(String login, String password) throws SQLException;
}
