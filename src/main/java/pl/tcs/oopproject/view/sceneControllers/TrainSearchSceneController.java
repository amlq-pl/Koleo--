package pl.tcs.oopproject.view.sceneControllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.view.Basket;
import pl.tcs.oopproject.view.ViewController;
import pl.tcs.oopproject.view.componentControllers.TrainPane;
import pl.tcs.oopproject.view.componentControllers.TrainPaneFactory;
import pl.tcs.oopproject.viewmodel.connection.*;
import pl.tcs.oopproject.viewmodel.discount.PricePLN;
import pl.tcs.oopproject.viewmodel.station.Station;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TrainSearchSceneController implements Initializable {
    private final Basket basket = new Basket();
    public Button BasketButton;

    public Button GoBackButton;
    public Button ExitButton;
    public Button ConfirmButton;
    public VBox ScrollablePane;
    private final ObservableList<TrainPane> ConnectionList = FXCollections.observableArrayList();
    private final ObservableList<String> StationObservable = FXCollections.observableArrayList(App.Stations);
    public ComboBox<String> DepStation;
    public ComboBox<String> ArrStation;
    public DatePicker ConnectionDate;
    public ComboBox<String> HourPicker = new ComboBox<>();

    private LocalDateTime getLocalDateTime() {
        LocalDate tempLocalDate = ConnectionDate.getValue();

        return LocalDateTime.of(tempLocalDate.getYear(),
                tempLocalDate.getMonth(),
                tempLocalDate.getDayOfMonth(),
                Integer.parseInt(HourPicker.getValue().substring(0,2)),
                Integer.parseInt(HourPicker.getValue().substring(3, 5)));
    }

    private void addAllPanes(ConnectionFinder finder) throws SQLException {
        // TODO: UNCOMMENT WHEN ConnectionFinder finished
//        System.out.println(finder.getRoutes());
        List<ConnectionWithTransfers> connections = null;
        try {
            connections = finder.getRoutes();
        } catch (SQLException e) {
            Stage stage = (Stage) ArrStation.getScene().getWindow();
            stage.close();
        } catch (NullPointerException e) {
            System.out.println("Null pointer");
            return;
        }

        assert connections != null;
        for (ConnectionWithTransfers con : connections) {
            TrainPane pane = TrainPaneFactory.createTrainPane(con, basket);
            ConnectionList.add(pane);
        }

        // TODO: ten kod jest tylko na czas testowania
//        Station start = new Station("Kraków", LocalDateTime.now(), LocalDateTime.now());
//        Station middle = new Station("Szczecinek", LocalDateTime.now(), LocalDateTime.now());
//        Station end = new Station("Warszawa", LocalDateTime.now(), LocalDateTime.now());
//
//        ArrayList<Station> list = new ArrayList<>();
//        list.add(start); list.add(middle);
//        TrainConnection trainConnection1 = new TrainConnection(list);
//        DirectConnection directConnection = new DirectConnection("PKP", 12, 21.37, TrainIsReservation.WITH_RESERVATION, trainConnection1);
//
//        ArrayList<Station> list2 = new ArrayList<>();
//        list2.add(middle); list2.add(end);
//        TrainConnection trainConnection2 = new TrainConnection(list2);
//        DirectConnection directConnection2 = new DirectConnection("PKP", 12, 21.37, TrainIsReservation.WITH_RESERVATION, trainConnection2);
//
//        ArrayList<String> arrayList = new ArrayList<>(); arrayList.add(start.getTown()); arrayList.add(middle.getTown());
//        ConnectionWithTransfers temp;
//        ArrayList<DirectConnection> d = new ArrayList<>();
//        d.add(directConnection);
//        d.add(directConnection2);
//        temp = new ConnectionWithTransfers(start, end, d, arrayList);
//        TrainPane tempPane = TrainPaneFactory.createTrainPane(temp, basket);
//        ConnectionList.addAll(tempPane);
        // ----->
    }

    public void ConfirmButtonClick() throws SQLException {
        if (!ConnectionList.isEmpty()) ConnectionList.clear();
        // TODO: add validation using validator FX or something like this

        LocalDateTime tempLocalDateTime = getLocalDateTime();

        ConnectionFinder finder =  ConnectionFinder.getConnectionFinder(DepStation.getValue(), ArrStation.getValue(), tempLocalDateTime);


        addAllPanes(finder);
    }
    public void GoBackButtonClick() {
        Stage thisStage = (Stage) GoBackButton.getScene().getWindow();
        thisStage.close();
        Stage prevStage = new Stage();
        prevStage.setScene(ViewController.getLandingScene());
        prevStage.show();
    }

    public void ExitButtonClick() {
        Stage thisStage = (Stage) ExitButton.getScene().getWindow();
        thisStage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> hours = IntStream.range(0, 24)
                .boxed()
                .flatMap(hour -> Stream.of(
                        String.format("%02d:00", hour),  // Full hour
                        String.format("%02d:30", hour)
                ))
                .collect(Collectors.toCollection(ArrayList::new));

        HourPicker.getItems().setAll(hours);
        DepStation.getItems().setAll(StationObservable);
        ArrStation.getItems().setAll(StationObservable);
        ConnectionDate.setValue(LocalDate.now());

        BasketButton.textProperty().bindBidirectional(basket.size, new StringConverter<Number>() {
            @Override
            public String toString(Number number) {
                return "KOSZYK (" + number + ")";
            }

            @Override
            public Number fromString(String s) {
                return null;
            }
        });

        ConnectionList.addListener((ListChangeListener<? super TrainPane>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    ScrollablePane.getChildren().addAll(change.getAddedSubList());
                }
                if (change.wasRemoved()) {
                    ScrollablePane.getChildren().removeAll(change.getRemoved());
                }
            }
        });
    }

    public void BasketButtonClick() {

    }
}
