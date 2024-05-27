package pl.tcs.oopproject.view.componentControllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.viewmodel.connection.DirectConnection;
import pl.tcs.oopproject.viewmodel.station.Station;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

public class StationPane extends AnchorPane {
    @FXML
    private Label TrainInfo;
    @FXML
    private VBox StationsView;
    @FXML
    private ObservableList<Station> observableList = FXCollections.observableArrayList();
    private StationPane (ArrayList<Station> list, DirectConnection train) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/station-pane.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
            TrainInfo.textProperty().setValue(train.getCompany() + " " + train.getNumber());

            observableList.addListener((ListChangeListener<? super Station>) change -> {
                while (change.next()) {
                    if (change.wasAdded()) {
                        List<AnchorPane> tempList = new ArrayList<>();
                        for (Station s : change.getAddedSubList()) {
                            s.display();
                            AnchorPane anchorPane = new AnchorPane();
                            anchorPane.setMaxWidth(300);
                            anchorPane.setMaxHeight(100);
                            Label nameLabel = new Label(s.getTown());
                            Label arrival = new Label(s.getArrivalTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                            Label departure = new Label(s.getDepartureTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                            HBox layout = new HBox();
                            anchorPane.getChildren().add(layout);
                            layout.setSpacing(10.0);
                            layout.getChildren().addAll(nameLabel, arrival, departure);
                        }
                        StationsView.getChildren().addAll(tempList);
                    }
                }
            });

            observableList.addAll(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static StationPane createStationPane(ArrayList<Station> list, DirectConnection train) {
        return new StationPane(list, train);
    }
}
