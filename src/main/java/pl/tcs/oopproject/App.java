package pl.tcs.oopproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.tcs.oopproject.model.DB;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try {
            DB.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("scenes/landing-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 480);
        stage.setTitle("Main app");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static String test() {
        return "test1";
    }
}
