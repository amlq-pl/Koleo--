package pl.tcs.oopproject.view.sceneControllers;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.tcs.oopproject.model.connection.ConnectionWithTransfers;
import pl.tcs.oopproject.view.Basket;
import pl.tcs.oopproject.view.componentControllers.BasketItem;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class BasketSceneController implements Initializable {
    private static Basket basket = new Basket();
    private static final ObservableList<BasketItem> items = FXCollections.observableArrayList();
    public Button GoBackButton;
    public Button CloseButton;
    public Label CostLabel;
    public Button BuyButton;
    public VBox BasketItems;

    public static void setBasket(Basket newBasket) {
        basket = newBasket;
        basket.itemsMap.forEach((key, value) -> {
            BasketItem basketItem = new BasketItem(key, basket);
            items.add(basketItem);
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        items.addListener((ListChangeListener<? super BasketItem>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    BasketItems.getChildren().addAll(change.getAddedSubList());
                }
                if (change.wasRemoved()) {
                    System.out.println("hello");
                    BasketItems.getChildren().removeAll(change.getRemoved());
                }
            }
        });
        basket.itemsMap.addListener((MapChangeListener<? super ConnectionWithTransfers, ? super IntegerProperty>) change -> {
            System.out.println("hello man");
            if (change.wasRemoved()) {
                items.removeIf(item -> item.getConnection().equals(change.getKey()));
                System.out.println("hello remover");
            }
        });
    }
}
