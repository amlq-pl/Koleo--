package pl.tcs.oopproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public static Connection connect() throws SQLException {
        try {
            String jdbcUrl = "jdbc:postgresql://localhost:5432/koleo_db";
            String user = "koleo_user";
            String password = "koleo";
            // Open a connection
            return DriverManager.getConnection(jdbcUrl, user, password);
        } catch (SQLException  e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}