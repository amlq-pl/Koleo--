package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.model.users.Person;

import java.sql.SQLException;

public interface UsersChecker {
    Person authenticate(String login, String password) throws SQLException;

    boolean insert(Person p, String login, String password) throws SQLException;

    boolean checkIfUserExists(String login) throws SQLException;

    boolean checkIfPersonExists(Person person) throws SQLException;
}
