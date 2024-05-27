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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TrainPane extends AnchorPane {
    @FXML
    private Label DepHour;
    @FXML
    private Label ArrHour;
    @FXML
    private Label PriceLabel;
    @FXML
    private Button ExtraButton;
    private final ConnectionWithTransfers privConnection;

    public TrainPane (ConnectionWithTransfers connection) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/train-pane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        privConnection = connection;
        try {
            loader.load();
            this.DepHour.textProperty().setValue(connection.getDepartureTime().toString());
            this.ArrHour.textProperty().setValue(connection.getArrivalTime().toString());
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

    public ConnectionWithTransfers getConnection() {
        return privConnection;
    }

}
