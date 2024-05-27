package pl.tcs.oopproject.postgresDatabaseIntegration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {
	public static Statement statement;
	public static Connection connection;
	
	static {
		try {
			connection = connect();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		try {
			assert connection != null;
			statement = connection.createStatement();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Connection connect() throws SQLException {
		try {
			String jdbcUrl = "jdbc:postgresql://localhost:5432/koleo_db";
			String user = "koleo_user";
			String password = "koleo";
			// Open a connection and create statement
			return DriverManager.getConnection(jdbcUrl, user, password);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
}