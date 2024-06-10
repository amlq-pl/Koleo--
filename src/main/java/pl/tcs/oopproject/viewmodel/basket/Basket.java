package pl.tcs.oopproject.viewmodel.basket;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.view.componentControllers.basket.BasketItem;

public class Basket {
    public ObservableList<MultiStopRoute> connectionList = FXCollections.observableArrayList();
    public ObservableList<BasketItem> basketItems = FXCollections.observableArrayList();
    public IntegerProperty size = new SimpleIntegerProperty();

    public Basket() {
    }

    public void basketClear() {
        connectionList.clear();
        basketItems.clear();
        size.setValue(0);
    }
}