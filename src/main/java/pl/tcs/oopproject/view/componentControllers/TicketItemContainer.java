package pl.tcs.oopproject.view.componentControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import pl.tcs.oopproject.model.connection.MultiStopRoute;

public class TicketItemContainer extends VBox {
    private final ObservableList<TicketItem> ticketItems = FXCollections.observableArrayList();
    private MultiStopRoute route;

    public TicketItemContainer(MultiStopRoute route) {
        this.route = route;


    }
}
