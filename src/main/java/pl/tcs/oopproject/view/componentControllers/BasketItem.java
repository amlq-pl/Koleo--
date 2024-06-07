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
import java.util.ArrayList;
import java.util.List;

public class BasketItem extends AnchorPane{
    public MultiStopRoute connection;
    public IntegerProperty count = new SimpleIntegerProperty();
    public int update = 0;
    private Basket basket = App.basket;
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

    public BasketItem(MultiStopRoute connection) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/basket-item.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            this.connection = connection;
            count.setValue(1);
            SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, count.getValue());
            Count.setValueFactory(factory);
            count.bind(Count.valueProperty());

            count.addListener(change -> update++);

            DepStation.textProperty().setValue(connection.originStation().town());
            ArrStation.textProperty().setValue(connection.destinationStation().town());
            DepHour.textProperty().setValue(connection.departureTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            ArrHour.textProperty().setValue(connection.arrivalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            Cost.textProperty().setValue(connection.cost().toString());

            DeleteButton.setOnMouseClicked(mouseEvent -> {
                basket.connectionList.removeAll(connection);

                List<BasketItem> toRemove = new ArrayList<>();
                for (BasketItem Item : basket.basketItems) {
                    if (Item.getConnection().equals(connection)) {
                        toRemove.add(Item);
                    }
                }

                basket.basketItems.removeAll(toRemove);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void increment() {
        Integer temp = count.getValue();
        SpinnerValueFactory<Integer> newFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, temp + 1);
        Count.setValueFactory(newFactory);
    }

    public MultiStopRoute getConnection() {
        return connection;
    }
}
