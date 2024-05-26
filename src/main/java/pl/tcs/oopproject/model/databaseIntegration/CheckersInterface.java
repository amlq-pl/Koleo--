package pl.tcs.oopproject.model.databaseIntegration;

import java.sql.SQLException;

public interface CheckersInterface {
    boolean checkIfUserExists(String login) throws SQLException;
}
