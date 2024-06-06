package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CheckersInterface {
	boolean checkIfUserExists(String login) throws SQLException;
	
	ArrayList<String> getAllStations() throws SQLException;
	ArrayList<Discount> getAllDiscounts() throws SQLException;
	ArrayList<Voucher> getAllVouchers() throws SQLException;
}
