package pl.tcs.oopproject;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.tcs.oopproject.view.ViewController;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        stage.setScene(ViewController.getLandingScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
