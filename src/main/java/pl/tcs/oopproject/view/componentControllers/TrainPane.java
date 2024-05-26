package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import pl.tcs.oopproject.App;

import java.io.IOException;

public class TrainPane extends AnchorPane {
    public TrainPane () {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/train-pane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void doSomething() {
        System.out.println("hello");
    }
}
