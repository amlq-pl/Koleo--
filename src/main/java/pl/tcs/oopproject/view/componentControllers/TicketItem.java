package pl.tcs.oopproject.view.componentControllers;

import javafx.scene.layout.AnchorPane;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.station.Station;

import java.util.ArrayList;

public class TicketItem extends AnchorPane {
    private MultiStopRoute route;
    public TicketItem(MultiStopRoute route) {
        this.route = route;
    }
}
