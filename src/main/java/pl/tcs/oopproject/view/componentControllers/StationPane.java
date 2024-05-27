package pl.tcs.oopproject.view.componentControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.viewmodel.connection.DirectConnection;
import pl.tcs.oopproject.viewmodel.station.Station;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;

public class StationPane extends AnchorPane {
    @FXML
    private Label TrainInfo;
    @FXML
    private Label BegStation;
    @FXML
    private Label BegHour;
    @FXML
    private Label EndStation;
    @FXML
    private Label EndHour;
    @FXML
    private ListView<String> StationList;
    @FXML
    private ObservableList<String> observableList = FXCollections.observableArrayList();
    private StationPane (ArrayList<DirectConnection> list, Station beg, Station last) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/station-pane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
            TrainInfo.textProperty().setValue(list.get(0).getCompany() + " " + list.get(0).getNumber());
            BegStation.textProperty().setValue(list.get(0).getFirstStation().getTown());
            BegHour.textProperty().setValue(list.get(0).getFirstStation().getDepartureTime()
                    .toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            EndStation.textProperty().setValue(list.get(list.size()-1).getLastStation().getTown());
            EndHour.textProperty().setValue(list.get(list.size()-1).getLastStation().getArrivalTime()
                    .toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
//            StationList.setItems(observableList.addAll(list.stream().map(x -> {
//                x.getStations().stream().
//            })));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static StationPane createStationPane(ArrayList<DirectConnection> list, Station begin, Station last) {
        return new StationPane(list, begin, last);
    }
}
