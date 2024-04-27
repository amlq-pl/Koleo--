package pl.tcs.oopproject;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        Label label = new Label("Main");
        root.getChildren().add(label);
        Scene scene = new Scene(root, 350, 300);
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
