package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.viewmodel.station.Station;

import java.time.format.DateTimeFormatter;

public class BasicStationPane extends AnchorPane {
    @FXML
    private Label StationName;
    @FXML
    private Label ArrDate;
    @FXML
    private Label DepDate;
    public BasicStationPane(Station station) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/basic-station-pane.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
            StationName.textProperty().setValue(station.getTown());
            ArrDate.textProperty().setValue(station.getArrivalTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            DepDate.textProperty().setValue(station.getDepartureTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
