package pl.tcs.oopproject;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.tcs.oopproject.postgresDatabaseIntegration.Checkers;
import pl.tcs.oopproject.view.ViewController;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.sql.SQLException;
import java.util.ArrayList;

public class App extends Application {
    private Checkers checkers = new Checkers();
    public static ArrayList<String> Stations;

    {
        try {
            Stations = checkers.getAllStations();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage stage) {
        stage.setScene(ViewController.getLandingScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
