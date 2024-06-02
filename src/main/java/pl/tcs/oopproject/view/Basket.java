package pl.tcs.oopproject.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.*;
import pl.tcs.oopproject.model.connection.ConnectionWithTransfers;

import java.util.Collections;
import java.util.Map;

public class Basket {
    public ObservableMap<ConnectionWithTransfers, IntegerProperty> itemsMap = FXCollections.observableHashMap();
    public IntegerProperty size = new SimpleIntegerProperty();

    {
        itemsMap.addListener((MapChangeListener<? super ConnectionWithTransfers, ? super IntegerProperty>) change -> {
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
