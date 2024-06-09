package pl.tcs.oopproject.model.databaseIntegration;

import java.sql.SQLException;
import java.time.LocalDate;

public interface UpdateUserProfile {
    void updateName(String login, String name) throws SQLException;

    void updateSurname(String login, String name) throws SQLException;

    boolean updateEmail(String login, String email) throws SQLException;

    void updateTelephoneNumber(String login, String phone) throws SQLException;

    void updateDateOfBirth(String login, LocalDate date) throws SQLException;

    boolean updateLogin(String oldLogin, String newLogin) throws SQLException;

    boolean updatePassword(String login, String oldPassword, String newPassword) throws SQLException;
}
