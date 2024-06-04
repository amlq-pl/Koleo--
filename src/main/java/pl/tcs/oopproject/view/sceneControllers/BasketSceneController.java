package pl.tcs.oopproject.view.sceneControllers;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.view.Basket;
import pl.tcs.oopproject.view.ViewController;
import pl.tcs.oopproject.view.componentControllers.BasketItem;

import java.net.URL;
import java.util.ResourceBundle;

public class BasketSceneController implements Initializable {
    private static Basket basket = new Basket();
    public static final ObservableList<BasketItem> items = FXCollections.observableArrayList();
    public Button GoBackButton;
    public Button CloseButton;
    public Label CostLabel;
    public Button BuyButton;
    public VBox BasketItems;


    public static void setBasket(Basket newBasket) {
        basket = newBasket;

        basket.itemsMap.addListener((MapChangeListener<? super MultiStopRoute, ? super IntegerProperty>) change -> {
            System.out.println("hello man");
            if (change.wasRemoved()) {
                items.removeIf(item -> item.getConnection().equals(change.getKey()));
                System.out.println("hello remover");
            }
        });

        basket.itemsMap.forEach((key, value) -> {
            System.out.println(basket);
            BasketItem basketItem = new BasketItem(key, basket);
            items.add(basketItem);
        });
    }

    public static Basket getBasket() {
        return basket;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        items.addListener((ListChangeListener<? super BasketItem>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    BasketItems.getChildren().addAll(change.getAddedSubList());
                }
                if (change.wasRemoved()) {
                    BasketItems.getChildren().removeAll(change.getRemoved());
                }
            }
        });
    }

    public void GoBackButtonClick() {
        Stage thisStage = (Stage) GoBackButton.getScene().getWindow();
        thisStage.close();
        Stage newStage = new Stage();
        newStage.setScene(ViewController.getTrainSearchScene());
        newStage.show();
    }

    public void CloseButtonClick() {
        Stage thisStage = (Stage) CloseButton.getScene().getWindow();
        thisStage.close();
    }
}
