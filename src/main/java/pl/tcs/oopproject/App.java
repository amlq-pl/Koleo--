package pl.tcs.oopproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.tcs.oopproject.model.DB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class App extends Application {
    public static Scene mainScene;
    public static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(App.class.getResource("scenes/landing-scene.fxml"));
        mainScene = new Scene(mainLoader.load(), 1000, 1000);
        mainStage = stage;
        stage.setTitle("Main app");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static String test() {
        return "test1";
    }
}
