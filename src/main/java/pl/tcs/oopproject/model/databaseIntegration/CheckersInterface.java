package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.ticket.Addition;
import pl.tcs.oopproject.model.ticket.LongTermTicketType;
import pl.tcs.oopproject.model.users.Person;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CheckersInterface {
    boolean checkIfUserExists(String login) throws SQLException;

    boolean checkIfPersonExists(Person person) throws SQLException;

    ArrayList<String> getAllStations() throws SQLException;

    ArrayList<Discount> getAllDiscounts() throws SQLException;

    ArrayList<Voucher> getAllVouchers() throws SQLException;

    ArrayList<LongTermTicketType> getAllLongTermTicketTypes() throws SQLException;

    ArrayList<Addition> getAllAdditions() throws SQLException;
}
