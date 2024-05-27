package pl.tcs.oopproject;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.tcs.oopproject.postgresDatabaseIntegration.Checkers;
import pl.tcs.oopproject.view.ViewController;

import java.sql.SQLException;
import java.util.ArrayList;

public class App extends Application {
	public static ArrayList<String> Stations;
	private final Checkers checkers = new Checkers();
	{
		try {
			Stations = checkers.getAllStations();
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
