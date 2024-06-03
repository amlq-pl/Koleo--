package pl.tcs.oopproject.view.componentControllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.connection.ConnectionWithTransfers;
import pl.tcs.oopproject.view.Basket;

import java.time.format.DateTimeFormatter;

public class BasketItem extends AnchorPane{
    private ConnectionWithTransfers connection;
    private Basket basket;
    @FXML
    private Spinner<Integer> Count;
    @FXML
    private Label Cost;
    @FXML
    private Label DepStation;
    @FXML
    private Label DepHour;
    @FXML
    private Label ArrStation;
    @FXML
    private Label ArrHour;
    @FXML
    private Button DeleteButton;

    public BasketItem(ConnectionWithTransfers connection, Basket basket) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/basket-item.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            this.connection = connection;
            this.basket = basket;
            IntegerProperty count = new SimpleIntegerProperty();
            count.bindBidirectional(basket.itemsMap.get(connection));
            SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, count.getValue());
            Count.setValueFactory(factory);
            count.bind(Count.valueProperty());
            DepStation.textProperty().setValue(connection.getFirstStation().getTown());
            ArrStation.textProperty().setValue(connection.getLastStation().getTown());
            DepHour.textProperty().setValue(connection.getDepartureTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            ArrHour.textProperty().setValue(connection.getArrivalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            Cost.textProperty().setValue(connection.getCost().toString());

            DeleteButton.setOnMouseClicked(mouseEvent -> {
                basket.itemsMap.remove(connection);
                System.out.println("Delete boy");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ConnectionWithTransfers getConnection() {
        return connection;
    }
}
