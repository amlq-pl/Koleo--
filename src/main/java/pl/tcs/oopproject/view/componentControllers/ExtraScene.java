package pl.tcs.oopproject.view.componentControllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.view.Basket;
import pl.tcs.oopproject.view.ViewController;
import pl.tcs.oopproject.viewmodel.connection.ConnectionWithTransfers;
import pl.tcs.oopproject.viewmodel.connection.DirectConnection;
import pl.tcs.oopproject.viewmodel.station.Station;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ExtraScene extends AnchorPane implements Initializable {
    private Basket thisBasket;
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
    private ConnectionWithTransfers thisConnection;
    private final ObservableList<StationPane> stationPanes = FXCollections.observableArrayList();

    public ExtraScene(TrainPane trainPane, Basket basket) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/extra-scene.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        thisConnection = trainPane.getConnection();
        thisBasket = basket;
        try {
            loader.load();
            addAllStations();
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
//        Stage thisStage = (Stage) BegStation.getScene().getWindow();
//        thisStage.close();
        thisBasket.itemsList.addAll(thisConnection);
        Popup popup = new Popup();
        Label popupText = new Label("Dodano połączenie do koszyka");
        popupText.setStyle("-fx-text-fill: green");
        popup.getContent().addAll(popupText);
        popup.setAutoHide(true);
        if (!popup.isShowing()) {
            popup.show(BegStation.getScene().getWindow());
        } else {
            popup.hide();
        }
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

//    private void addToStationList() {
//        StationPane temp = StationPane.createStationPane(station);
//        stationPanes.addAll(temp);
//    }
    private void addAllStations() {
        ArrayList<ArrayList<Station>> routes = thisConnection.getRoute();
        System.out.println(thisConnection.getRoute());
        ArrayList<DirectConnection> trains = (ArrayList<DirectConnection>) thisConnection.getTrains();
        for (int i = 0; i < routes.size(); i++) {
            stationPanes.addAll(StationPane.createStationPane(routes.get(i), trains.get(i)));
        }
    }
}
