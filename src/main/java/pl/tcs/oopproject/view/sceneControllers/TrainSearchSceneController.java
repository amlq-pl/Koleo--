package pl.tcs.oopproject.view.sceneControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.tcs.oopproject.view.ViewController;
import pl.tcs.oopproject.view.componentControllers.TrainPane;
import pl.tcs.oopproject.viewmodel.connection.ConnectionFinder;

public class TrainSearchSceneController {

    public Button GoBackButton;
    public Button ExitButton;
    public Button ConfirmButton;
    public VBox ScrollablePane;
    private static ConnectionFinder finder;

//    {
//        finder = new ConnectionFinder();
//    }

    private final ObservableList<TrainPane> ConnectionList = FXCollections.observableArrayList();

    public void ConfirmButtonClick()  {
        // TODO: add validation using validator FX or something like this
        System.out.println("Hello yo");
        TrainPane pane = new TrainPane();
        ConnectionList.add(pane);
        ScrollablePane.getChildren().addAll(ConnectionList);
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
}
