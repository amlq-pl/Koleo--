package pl.tcs.oopproject.postgresDatabase;

import pl.tcs.oopproject.model.databaseIntegration.CheckersInterface;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Checkers implements CheckersInterface {
    public boolean checkIfUserExists(String login) throws SQLException {
        Statement statement= DB.statement;

        String query="select * "+
                "from konto ko "+
                "where ko.login=\'"+login+"\'";

        ResultSet resultSet=statement.executeQuery(query);
        return resultSet.next();
    }
}
