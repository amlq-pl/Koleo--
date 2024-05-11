package pl.tcs.oopproject.view.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;

import java.io.IOException;


public class LandingSceneController {
    public Button SignUpButton;
    public Button LogInButton;

    @FXML
    protected void SignUpClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("scenes/signup-scene.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void LogInClicked() {
    }
}
