package pl.tcs.oopproject.view.componentControllers;

import javafx.scene.layout.AnchorPane;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.station.Station;

import java.util.ArrayList;

public class TicketItem extends AnchorPane {
    private final ArrayList<Station> route;
    public TicketItem(ArrayList<Station> route) {
        this.route = route;
    }
}
