package pl.tcs.oopproject.view.sceneControllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.tcs.oopproject.view.ViewController;
import pl.tcs.oopproject.view.componentControllers.TrainPane;
import pl.tcs.oopproject.view.componentControllers.TrainPaneFactory;
import pl.tcs.oopproject.viewmodel.connection.ConnectionFinder;
import pl.tcs.oopproject.viewmodel.connection.ConnectionWithTransfers;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TrainSearchSceneController implements Initializable {
    public Button GoBackButton;
    public Button ExitButton;
    public Button ConfirmButton;
    public VBox ScrollablePane;
    private final ObservableList<TrainPane> ConnectionList = FXCollections.observableArrayList();
    public TextField DepStation;
    public TextField ArrStation;
    public DatePicker ConnectionDate;
    public ComboBox<String> HourPicker = new ComboBox<>();
    private int cnt = 0;

    private LocalDateTime getLocalDateTime() {
        LocalDate tempLocalDate = ConnectionDate.getValue();

        return LocalDateTime.of(tempLocalDate.getYear(),
                tempLocalDate.getMonth(),
                tempLocalDate.getDayOfMonth(),
                Integer.parseInt(HourPicker.getValue().substring(0,2)),
                Integer.parseInt(HourPicker.getValue().substring(3, 5)));
    }

    private void addAllPanes(ConnectionFinder finder) {
        List<ConnectionWithTransfers> connections = finder.getRoutes();

//        Stream<ConnectionWithTransfers> connectionWithTransfersStream = connections.stream();
//        connectionWithTransfersStream.map(x -> {
//            TrainPane = TrainPaneFactory.createTrainPane(x.getDepartureTime(), x.getArrivalTime());
//        })
//
//        TrainPane pane = TrainPaneFactory.createTrainPane();
//        pane.setId(String.valueOf(cnt++));
//        ConnectionList.add(pane);
    }

    public void ConfirmButtonClick()  {
        // TODO: add validation using validator FX or something like this

        LocalDateTime tempLocalDateTime = getLocalDateTime();
        ConnectionFinder finder =  new ConnectionFinder(DepStation.textProperty().get(), ArrStation.textProperty().get(), tempLocalDateTime);

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

        ConnectionList.addListener((ListChangeListener<? super TrainPane>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    ScrollablePane.getChildren().addAll(change.getAddedSubList());
                }
            }
        });
    }
}
