package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;

public class ExtraScene extends AnchorPane {
    public ExtraScene(TrainPane trainPane) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/extra-scene.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
            Scene scene = new Scene(this);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
