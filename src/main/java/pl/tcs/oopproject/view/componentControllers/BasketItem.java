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
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.view.Basket;

import java.time.format.DateTimeFormatter;

public class BasketItem extends AnchorPane{
    private MultiStopRoute connection;
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

    public BasketItem(MultiStopRoute connection, Basket basket) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/basket-item.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            this.connection = connection;
            this.basket = basket;
            System.out.println(basket);
            IntegerProperty count = new SimpleIntegerProperty();
            count.bindBidirectional(basket.itemsMap.get(connection));
            SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, count.getValue());
            Count.setValueFactory(factory);
            count.bind(Count.valueProperty());

            DepStation.textProperty().setValue(connection.originStation().town());
            ArrStation.textProperty().setValue(connection.destinationStation().town());
            DepHour.textProperty().setValue(connection.departureTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            ArrHour.textProperty().setValue(connection.arrivalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            Cost.textProperty().setValue(connection.cost().toString());

            DeleteButton.setOnMouseClicked(mouseEvent -> {
                basket.itemsMap.remove(connection);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MultiStopRoute getConnection() {
        return connection;
    }
}
