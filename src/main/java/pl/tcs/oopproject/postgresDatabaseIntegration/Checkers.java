package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.CheckersInterface;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;

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
		ResultSet resultSet = DB.statement.executeQuery("select s.nazwa from stacje s order by s.nazwa;");
		ArrayList<String> stations = new ArrayList<>();
		while (resultSet.next()) {
			stations.add(resultSet.getString("nazwa"));
		}
		return stations;
	}

	@Override
	public ArrayList<Discount> getAllDiscounts() throws SQLException {
		//Ulgi
		ArrayList<Discount> discounts = new ArrayList<>();
		ResultSet rs = DB.statement.executeQuery("select u.nazwa,u.znizka from ulgi u order by u.nazwa;");
		while (rs.next()) {
			discounts.add(new Discount(rs.getString("nazwa"),rs.getInt("znizka")));
		}
		return discounts;
	}

	@Override
	public ArrayList<Voucher> getAllVouchers() throws SQLException {
		//Znizki
		ArrayList<Voucher> vouchers = new ArrayList<>();
		ResultSet rs = DB.statement.executeQuery("select r.nazwa,r.znizka from rabaty r " +
				"where r.data_wprowadzenia<=now() and r.data_waznosci>=now() order by r.nazwa; ");
		while (rs.next()) {
			vouchers.add(new Voucher(rs.getString("nazwa"),rs.getInt("znizka")));
		}
		return vouchers;
	}


}
