package pl.tcs.oopproject.model.databaseIntegration;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CheckersInterface {
    boolean checkIfUserExists(String login) throws SQLException;
    ArrayList<String> getAllStations() throws SQLException;
}
