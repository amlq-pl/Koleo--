package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.CheckersInterface;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.PricePLN;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.ticket.Addition;
import pl.tcs.oopproject.model.ticket.LongTermTicketType;
import pl.tcs.oopproject.model.users.Person;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Period;
import java.util.ArrayList;

public class Checkers implements CheckersInterface {
	public boolean checkIfUserExists(String login) throws SQLException {
		PreparedStatement statement = DB.connection.prepareStatement("select * from konto ko where ko.login=?");
		statement.setString(1, login);
		ResultSet resultSet = statement.executeQuery();
		return resultSet.next();
	}

	@Override
	public boolean checkIfPersonExists(Person person) throws SQLException {
		PreparedStatement statement = DB.connection.prepareStatement("select * from klienci k where k.email=?");
		statement.setString(1, person.getEmailAddress());
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

	@Override
	public ArrayList<LongTermTicketType> getAllLongTermTicketTypes() throws SQLException {
		ArrayList<LongTermTicketType> ticketTypes = new ArrayList<>();
		PreparedStatement ps=DB.connection.prepareStatement("select * from cennik_biletow_okresowych join przewoznicy on cennik_biletow_okresowych.id_przewoznika = przewoznicy.id_przewoznika");
		ResultSet rs=ps.executeQuery();
		while (rs.next()) {
			int days=0;
			String[] parts=rs.getString("okres_waznosci").split(" ");
			for (String part : parts) {
            if (part.endsWith("days")) {
                days = Integer.parseInt(part.replace("days", "").trim());
                break;
            }
        }
			ticketTypes.add(new LongTermTicketType(Period.ofDays(days),new PricePLN(rs.getDouble("cena_bazowa")),rs.getString("nazwa_skrocona")));
		}
		return ticketTypes;
	}

	@Override
	public ArrayList<Addition> getAllAdditions() throws SQLException {
		ArrayList<Addition> additions = new ArrayList<>();
		PreparedStatement ps=DB.connection.prepareStatement("select * from koszty_udogodnien where data_poczatkowa<=now() and data_koncowa>=now();");
		ResultSet rs=ps.executeQuery();
		while (rs.next()) {
			additions.add(new Addition(rs.getString("nazwa"),rs.getDouble("koszt_procentowy")));
		}
		return additions;
	}


}
