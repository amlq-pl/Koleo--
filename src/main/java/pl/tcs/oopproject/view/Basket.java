package pl.tcs.oopproject.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import pl.tcs.oopproject.model.connection.MultiStopRoute;

public class Basket {
    public ObservableMap<MultiStopRoute, IntegerProperty> itemsMap = FXCollections.observableHashMap();
    public IntegerProperty size = new SimpleIntegerProperty();

    {
        itemsMap.addListener((MapChangeListener<? super MultiStopRoute, ? super IntegerProperty>) change -> {
            if (change.wasAdded()) {
                size.setValue(size.getValue() + 1);
            }
            if (change.wasRemoved()) {
                size.setValue(size.getValue() - 1);
            }
        });
    }

    public Basket() {
    }
}
