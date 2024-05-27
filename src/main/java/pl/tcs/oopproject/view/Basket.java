package pl.tcs.oopproject.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import pl.tcs.oopproject.model.connection.ConnectionWithTransfers;

public class Basket {
	public ObservableList<ConnectionWithTransfers> itemsList = FXCollections.observableArrayList();
	public IntegerProperty size = new SimpleIntegerProperty();
	
	{
		itemsList.addListener((ListChangeListener<? super ConnectionWithTransfers>) change -> {
			while (change.next()) {
				if (change.wasAdded()) {
					size.setValue(size.getValue() + 1);
				}
				if (change.wasRemoved()) {
					size.setValue(size.getValue() - 1);
				}
			}
		});
	}
	
	public Basket() {
	}
}
