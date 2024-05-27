package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.connection.ConnectionWithTransfers;
import pl.tcs.oopproject.view.Basket;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class TrainPane extends AnchorPane {
    private Basket thisBasket;
    @FXML
    private Label DepHour;
    @FXML
    private Label ArrHour;
    @FXML
    private Label PriceLabel;
    @FXML
    private Button ExtraButton;
    private final ConnectionWithTransfers privConnection;

    public TrainPane (ConnectionWithTransfers connection, Basket basket) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/train-pane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        privConnection = connection;
        thisBasket = basket;
        try {
            loader.load();
            this.DepHour.textProperty().setValue(connection.getDepartureTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            this.ArrHour.textProperty().setValue(connection.getArrivalTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            this.PriceLabel.textProperty().setValue(String.valueOf(connection.getCost()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void ExtraButtonClick() {
        Stage thisStage = (Stage) ExtraButton.getScene().getWindow();
        thisStage.close();
        ExtraScene scene = new ExtraScene(this, thisBasket);
    }

    public ConnectionWithTransfers getConnection() {
        return privConnection;
    }

}
