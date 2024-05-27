package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.CheckersInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Checkers implements CheckersInterface {
	public boolean checkIfUserExists(String login) throws SQLException {
		PreparedStatement statement = DB.connection.prepareStatement("select * from konto ko where ko.login=?");
		statement.setString(1, login);
		ResultSet resultSet = statement.executeQuery();
		return resultSet.next();
	}
	
	@Override
	public ArrayList<String> getAllStations() throws SQLException {
		String query = "select s.nazwa from stacje s order by s.nazwa;";
		ResultSet resultSet = DB.statement.executeQuery(query);
		ArrayList<String> stations = new ArrayList<>();
		while (resultSet.next()) {
			stations.add(resultSet.getString("nazwa"));
		}
		return stations;
	}
	
	
}
