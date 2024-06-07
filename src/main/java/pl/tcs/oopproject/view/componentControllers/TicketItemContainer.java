package pl.tcs.oopproject.view.componentControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.assignedSeat.AssignedSeat;
import pl.tcs.oopproject.model.connection.MultiStopRoute;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class TicketItemContainer extends AnchorPane implements Initializable {
    private final ObservableList<TicketItem> ticketItems = FXCollections.observableArrayList();
    private final MultiStopRoute currentRoute;
    private final List<AssignedSeat> seatList;
    @FXML
    private VBox TicketContainer;

    public TicketItemContainer(MultiStopRoute route, List<AssignedSeat> seatList) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/ticket-container.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        this.currentRoute = route;
        this.seatList = seatList;

        int size = seatList.size();
        IntStream.range(0, size).forEach(c -> {
            TicketItem item = new TicketItem(currentRoute.route().get(c), seatList.get(c),
                    currentRoute.trains().get(c));
            TicketContainer.getChildren().addAll(item);
        });

        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MultiStopRoute getConnection() {
        return currentRoute;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
