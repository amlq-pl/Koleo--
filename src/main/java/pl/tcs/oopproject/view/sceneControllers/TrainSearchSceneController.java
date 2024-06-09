package pl.tcs.oopproject.view.sceneControllers;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.view.Basket;
import pl.tcs.oopproject.view.SimpleDateProperty;
import pl.tcs.oopproject.view.ViewController;
import pl.tcs.oopproject.view.componentControllers.TrainPane;
import pl.tcs.oopproject.view.componentControllers.TrainPaneFactory;
import pl.tcs.oopproject.viewmodel.connection.TrainConnectionFinder;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TrainSearchSceneController implements Initializable {
    private static final StringProperty InitialDepStation = new SimpleStringProperty("");
    private static final StringProperty InitialArrStation = new SimpleStringProperty("");
    private static final Property<LocalDate> InitialDate = new SimpleDateProperty();
    private static final StringProperty InitialHour = new SimpleStringProperty("");
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
    public ComboBox<String> SortBy = new ComboBox<>();

    public static void setInitialData(String DepStation, String ArrStation, LocalDate Date, String Hour) {
        InitialDepStation.setValue(DepStation);
        InitialArrStation.setValue(ArrStation);
        InitialDate.setValue(Date);
        InitialHour.setValue(Hour);
    }


    private LocalDateTime getLocalDateTime() {
        LocalDate tempLocalDate = ConnectionDate.getValue();

        return LocalDateTime.of(tempLocalDate.getYear(),
                tempLocalDate.getMonth(),
                tempLocalDate.getDayOfMonth(),
                Integer.parseInt(HourPicker.getValue().substring(0,2)),
                Integer.parseInt(HourPicker.getValue().substring(3, 5)));
    }

    private void addAllPanes(TrainConnectionFinder finder) {
        List<MultiStopRoute> connections = null;
        try {
            if (SortBy.valueProperty().getValue().equals("Po czasie")) {
                connections = finder.findTrainRoutes();
            } else if (SortBy.valueProperty().getValue().equals("Po cenie")) {
                connections = finder.findCheapestTrainRoutes();
            } else {
                connections = finder.getDirectTrainRoutes();
            }
        } catch (SQLException e) {
            Stage stage = (Stage) ArrStation.getScene().getWindow();
            stage.close();
        } catch (NullPointerException e) {
            System.out.println("Null pointer");
            return;
        }

        assert connections != null;
        for (MultiStopRoute con : connections) {
            TrainPane pane = TrainPaneFactory.createTrainPane(con);
            ConnectionList.add(pane);
        }

    }

    public void ConfirmButtonClick() throws SQLException {
        if (!ConnectionList.isEmpty()) ConnectionList.clear();
        // TODO: add validation using validator FX or something like this

        LocalDateTime tempLocalDateTime = getLocalDateTime();
        String departure = DepStation.getValue();
        String arrival = ArrStation.getValue();

        if (departure.equals(arrival)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Miasto początkowe i końcowe muszą być różne");
            a.showAndWait();
            return;
        }

        TrainConnectionFinder finder =  TrainConnectionFinder.getConnectionFinder(departure, arrival, tempLocalDateTime);

        addAllPanes(finder);
    }
    public void GoBackButtonClick() {
        if (ActiveUser.getActiveUser() != null) {
            ActiveUser.logOut();
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("WYLOGOWANO Z KONTA");
            a.showAndWait();
        }
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
        SortBy.getItems().setAll(FXCollections.observableArrayList(new ArrayList<>(List.of("Po czasie", "Po cenie", "Bez przesiadek"))));
        SortBy.valueProperty().setValue("Po czasie");

        DepStation.valueProperty().bindBidirectional(InitialDepStation);
        ArrStation.valueProperty().bindBidirectional(InitialArrStation);
        ConnectionDate.valueProperty().bindBidirectional(InitialDate);
        HourPicker.valueProperty().bindBidirectional(InitialHour);

        if (ActiveUser.getActiveUser() != null) GoBackButton.textProperty().setValue("WYLOGUJ SIĘ");

        BasketButton.textProperty().bindBidirectional(basket.size, new StringConverter<>() {
            @Override
            public String toString(Number number) {
                return "KOSZYK";
            } // TODO: wrocic do starego koszyka

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
        Stage thisStage = (Stage) BasketButton.getScene().getWindow();
        thisStage.close();
        Stage newStage = new Stage();
        newStage.setScene(ViewController.getBasketScene());
        newStage.show();
    }

    public void AccountButtonClick() {
        if (ActiveUser.getActiveUser() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("ZALOGUJ SIĘ ABY KORZYSTAĆ Z TEJ OPCJI");
            a.showAndWait();
        } else {

            Stage thisStage = (Stage) BasketButton.getScene().getWindow();
            thisStage.close();

            FXMLLoader loader = new FXMLLoader(App.class.getResource("scenes/account-scene.fxml"));
            Parent p = null;
            try {
                p = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Scene scene = new Scene(p);

            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
        }
    }

    public void BuyLongTermTicket() {
        System.out.println(ActiveUser.getActiveUser());
        if (ActiveUser.getActiveUser() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Zaloguj się aby kupić");
            alert.showAndWait();
            return;
        }

        Stage newStage = new Stage();
        newStage.setScene(ViewController.getLongTermTicketBuyScene());
        newStage.show();
    }
}
