package pl.tcs.oopproject.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
    public static Statement connection;

    static {
        try {
            connection = connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Statement connect() throws SQLException {
        try {
            String jdbcUrl = "jdbc:postgresql://localhost:5432/koleo_db";
            String user = "koleo_user";
            String password = "koleo";
            // Open a connection and create statement
            return DriverManager.getConnection(jdbcUrl, user, password).createStatement();
        } catch (SQLException  e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}