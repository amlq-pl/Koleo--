package pl.tcs.oopproject.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.view.componentControllers.BasketItem;

public class Basket {
    public ObservableList<MultiStopRoute> connectionList = FXCollections.observableArrayList();
    public ObservableList<BasketItem> basketItems = FXCollections.observableArrayList();
    public IntegerProperty size = new SimpleIntegerProperty();

    public Basket() {

    }
}
