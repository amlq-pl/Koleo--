package pl.tcs.oopproject.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Checkers {
    public static boolean checkIfUserExists(String login) throws SQLException {
        Statement statement=DB.statement;

        String query="select * "+
                "from konto ko "+
                "where ko.login=\'"+login+"\'";

        ResultSet resultSet=statement.executeQuery(query);
        return !resultSet.next();
    }
}
