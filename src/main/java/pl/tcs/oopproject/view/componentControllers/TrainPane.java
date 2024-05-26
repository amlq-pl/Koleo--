package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.viewmodel.connection.ConnectionWithTransfers;
import java.io.IOException;
import java.time.LocalDateTime;

public class TrainPane extends AnchorPane {
    @FXML
    private Label DepHour;
    @FXML
    private Label ArrHour;
    @FXML
    private Label PriceLabel;
    @FXML
    private Button ExtraButton;
    private ConnectionWithTransfers connection;

    public TrainPane (ConnectionWithTransfers connection) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/train-pane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
            this.connection = connection;
            this.DepHour.textProperty().setValue(String.valueOf(connection.getDepartureTime()));
            this.ArrHour.textProperty().setValue(String.valueOf(connection.getArrivalTime()));
            this.PriceLabel.textProperty().setValue(String.valueOf(connection.getCost()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void ExtraButtonClick() {
        System.out.println("hello");
        System.out.println(ExtraButton.getParent());
        Stage thisStage = (Stage) ExtraButton.getScene().getWindow();
        thisStage.close();
        ExtraScene scene = new ExtraScene(this);
    }
    @FXML
    protected void doSomething() {
        System.out.println("hello");
    }

}
