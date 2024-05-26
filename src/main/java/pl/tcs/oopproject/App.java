package pl.tcs.oopproject;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.tcs.oopproject.view.ViewController;

import java.util.ArrayList;

public class App extends Application {
    public static ArrayList<String> Stations;
    @Override
    public void start(Stage stage) {
        stage.setScene(ViewController.getLandingScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
