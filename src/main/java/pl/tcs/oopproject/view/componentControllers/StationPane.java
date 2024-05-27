package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.viewmodel.station.Station;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class StationPane extends AnchorPane {
    @FXML
    private Label StationName;
    @FXML
    private Label ArrivalDate;
    @FXML
    private Label DepartureDate;
    private StationPane (Station station) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/station-pane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
            StationName.textProperty().setValue(station.getTown());
            ArrivalDate.textProperty().setValue(station.getArrivalTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            DepartureDate.textProperty().setValue(station.getDepartureTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static StationPane createStationPane(Station station) {
        return new StationPane(station);
    }
}
