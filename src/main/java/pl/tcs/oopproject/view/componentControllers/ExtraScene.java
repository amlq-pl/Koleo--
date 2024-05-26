package pl.tcs.oopproject.view.componentControllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.view.ViewController;
import pl.tcs.oopproject.viewmodel.station.Station;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ExtraScene extends AnchorPane implements Initializable {
    @FXML
    private Label BegStation;
    @FXML
    private Label EndStation;
    @FXML
    private Label BegDate;
    @FXML
    private Label ArrDate;
    @FXML
    private VBox ListOfStations;
    private final ObservableList<StationPane> stationPanes = FXCollections.observableArrayList();

    public ExtraScene(TrainPane trainPane) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/extra-scene.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
            addAllStations(trainPane);
            Scene scene = new Scene(this);
            Stage stage = new Stage();
            BegStation.textProperty().setValue(trainPane.getConnection()
                    .getFirstStation().getTown());
            EndStation.textProperty().setValue(trainPane.getConnection()
                    .getLastStation().getTown());
            BegDate.textProperty().setValue(trainPane.getConnection()
                    .getFirstStation()
                    .getDepartureTime()
                    .toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            ArrDate.textProperty().setValue(trainPane.getConnection()
                    .getLastStation()
                    .getArrivalTime()
                    .toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void ExitButtonClick() {
        Stage thisStage = (Stage) BegStation.getScene().getWindow();
        thisStage.close();
    }
    @FXML
    protected void BackButtonClick() {
        Stage thisStage = (Stage) BegStation.getScene().getWindow();
        thisStage.close();
        Stage newStage = new Stage();
        Scene scene = ViewController.getTrainSearchScene();
        newStage.setScene(scene);
        newStage.show();
    }
    @FXML
    protected void AddToBasketClick() {
        Stage thisStage = (Stage) BegStation.getScene().getWindow();
        thisStage.close();
        // TODO: FINISH IMPLEMENTATION
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stationPanes.addListener((ListChangeListener<? super StationPane>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    ListOfStations.getChildren().addAll(change.getAddedSubList());
                }
            }
        });
    }

    private void addToStationList(Station station) {
        StationPane temp = StationPane.createStationPane(station);
        stationPanes.addAll(temp);
    }
    private void addAllStations(TrainPane pane) {
        addToStationList(pane.getConnection().getFirstStation());
        for (Station s : pane.getConnection().getTransferStations()) {
            addToStationList(s);
        }
        addToStationList(pane.getConnection().getLastStation());
    }
}
