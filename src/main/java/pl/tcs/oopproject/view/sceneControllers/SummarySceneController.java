package pl.tcs.oopproject.view.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.tcs.oopproject.model.ticket.SingleJourneyTrainTicket;
import pl.tcs.oopproject.view.ViewController;
import pl.tcs.oopproject.view.componentControllers.PurchaseSingleTicketPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SummarySceneController implements Initializable {
    private ArrayList<SingleJourneyTrainTicket> list = new ArrayList<>();
    public Button CloseButton;
    public Button ConfirmButton;
    public VBox TicketContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setList(ArrayList<SingleJourneyTrainTicket> l) {
        list = l;

        list.forEach(c -> {
            PurchaseSingleTicketPane pane = new PurchaseSingleTicketPane(c);
            TicketContainer.getChildren().addAll(pane);
        });
    }

    public void ConfirmButtonClick() {
        Stage thisStage = (Stage) CloseButton.getScene().getWindow();
        thisStage.close();
        Stage newStage = new Stage();
        newStage.setScene(ViewController.getTrainSearchScene());
        newStage.show();
    }

    public void ExitButtonClick() {
        Stage thisStage = (Stage) CloseButton.getScene().getWindow();
        thisStage.close();
    }
}
