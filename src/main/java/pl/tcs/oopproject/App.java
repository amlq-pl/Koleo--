package pl.tcs.oopproject;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.tcs.oopproject.view.ViewController;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setScene(ViewController.getLandingScene());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static String test() {
        return "test1";
    }
}
