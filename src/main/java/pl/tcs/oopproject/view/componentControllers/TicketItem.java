package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.assignedSeat.AssignedSeat;
import pl.tcs.oopproject.model.connection.ScheduledTrain;
import pl.tcs.oopproject.model.station.Station;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TicketItem extends AnchorPane implements Initializable {
    @FXML
    private Label DepStation;
    @FXML
    private Label DepHour;
    @FXML
    private Label ArrStation;
    @FXML
    private Label ArrHour;
    @FXML
    private Label TrainName;
    @FXML
    private Label Seat;
    private final ArrayList<Station> stations;
    private final ScheduledTrain train;
    AssignedSeat seat;

    public TicketItem(ArrayList<Station> stationList, AssignedSeat seat, ScheduledTrain train) {
        this.stations = stationList;
        this.seat = seat;
        this.train = train;

        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/ticket-item.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DepStation.textProperty().setValue(stations.get(0).town());
        DepHour.textProperty().setValue(stations.get(0)
                .departureTime()
                .format(DateTimeFormatter.ofPattern("HH:mm")));
        ArrStation.textProperty().setValue(stations.get(stations.size() - 1).town());
        ArrHour.textProperty().setValue(stations.get(stations.size() - 1)
                .arrivalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm")));
        TrainName.textProperty().setValue(train.getCompany() + " " + train.getNumber());
        Seat.textProperty().setValue(seat.toString());
    }
}
