package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.history.HistorySingleJourneyTicket;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HistorySingleTicketPane extends AnchorPane implements Initializable {
    HistorySingleJourneyTicket ticket;
    @FXML
    private Label IdLabel;
    @FXML
    private Label NameLabel;
    @FXML
    private Label SurnameLabel;
    @FXML
    private Label DepStationLabel;
    @FXML
    private Label DepHourLabel;
    @FXML
    private Label ArrStationLabel;
    @FXML
    private Label ArrStationHour;
    @FXML
    private Label CostLabel;
    @FXML
    private Button RefundButton;
    @FXML
    private VBox ButtonContainer;
    public HistorySingleTicketPane(HistorySingleJourneyTicket ticket) {
        this.ticket = ticket;

        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/history-single-ticket-pane.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IdLabel.textProperty().setValue(String.valueOf(ticket.id()));
        NameLabel.textProperty().setValue(ticket.person().getName());
        SurnameLabel.textProperty().setValue(ticket.person().getSurname());
        DepStationLabel.textProperty().setValue(ticket.departureStation());
        DepHourLabel.textProperty().setValue(ticket.departureTime()
                .format(DateTimeFormatter.ofPattern("HH:mm")));
        ArrStationLabel.textProperty().setValue(ticket.arrivalStation());
        ArrStationHour.textProperty().setValue(ticket.arrivalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm")));
        CostLabel.textProperty().setValue(ticket.cost().toString());

        RefundButton.setOnAction(e -> {
            try {
                ButtonContainer.getChildren().removeAll(RefundButton);
                ticket.refundTicket();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}
