package pl.tcs.oopproject.view.componentControllers.station;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.connection.ScheduledTrain;
import pl.tcs.oopproject.model.station.Station;
import pl.tcs.oopproject.view.componentControllers.basket.BasketItem;
import pl.tcs.oopproject.view.componentControllers.station.StationPane;
import pl.tcs.oopproject.view.componentControllers.train.TrainPane;
import pl.tcs.oopproject.viewmodel.basket.Basket;
import pl.tcs.oopproject.view.ViewController;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ExtraScene extends AnchorPane implements Initializable {
    private final ObservableList<StationPane> stationPanes = FXCollections.observableArrayList();
    private final Basket thisBasket = App.basket;
    private final MultiStopRoute thisConnection;
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

    public ExtraScene(TrainPane trainPane) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/extra-scene.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        thisConnection = trainPane.getConnection();
        try {
            loader.load();
            addAllStations();
            Scene scene = new Scene(this);
            Stage stage = new Stage();
            BegStation.textProperty().setValue(trainPane.getConnection()
                    .originStation().town());
            EndStation.textProperty().setValue(trainPane.getConnection()
                    .destinationStation().town());
            BegDate.textProperty().setValue(trainPane.getConnection()
                    .originStation()
                    .departureTime()
                    .toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            ArrDate.textProperty().setValue(trainPane.getConnection()
                    .destinationStation()
                    .arrivalTime()
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
        if (thisConnection.departureTime().isBefore(LocalDateTime.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Nie można kupić biletu z przeszłości!!!");
            alert.showAndWait();
            return;
        }

        if (thisBasket.connectionList.contains(thisConnection)) {
            for (BasketItem Item : thisBasket.basketItems) {
                if (Item.getConnection().equals(thisConnection)) {
                    Item.increment();
                }
            }
        } else {
            thisBasket.connectionList.add(thisConnection);
            thisBasket.basketItems.add(new BasketItem(thisConnection));
        }

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

    private void addAllStations() {
        ArrayList<ArrayList<Station>> routes = thisConnection.route();
        ArrayList<ScheduledTrain> trains = (ArrayList<ScheduledTrain>) thisConnection.trains();
        for (int i = 0; i < routes.size(); i++) {
            stationPanes.addAll(StationPane.createStationPane(routes.get(i), trains.get(i)));
        }
    }
}
