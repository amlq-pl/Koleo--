package pl.tcs.oopproject;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.ticket.Addition;
import pl.tcs.oopproject.model.ticket.LongTermTicketType;
import pl.tcs.oopproject.postgresDatabaseIntegration.Checkers;
import pl.tcs.oopproject.view.Basket;
import pl.tcs.oopproject.view.ViewController;

import java.sql.SQLException;
import java.util.ArrayList;

public class App extends Application {
	public static ArrayList<String> Stations;
	public static ArrayList<Discount> Discounts;
	public static ArrayList<Voucher> Vouchers;
	public static ArrayList<Addition> Additions;
	public static ArrayList<LongTermTicketType> LongTermTicketTypes;
	public static Discount DEFAULT_DISCOUNT;
	private final Checkers checkers = new Checkers();

	public static Basket basket = new Basket();
	{
		try {
			Stations = checkers.getAllStations();
			Discounts = checkers.getAllDiscounts();
			Vouchers = checkers.getAllVouchers();
			Additions = checkers.getAllAdditions();
			LongTermTicketTypes = checkers.getAllLongTermTicketTypes();

			for (Discount d : Discounts) {
				if (d.discount().equals("Normalny")) {
					DEFAULT_DISCOUNT = d;
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch();
	}
	
	@Override
	public void start(Stage stage) {
		stage.setScene(ViewController.getLandingScene());
		stage.show();
	}
}
