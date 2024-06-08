package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import pl.tcs.oopproject.model.history.HistoryLongTermTicket;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HistoryLongTicketPane extends AnchorPane implements Initializable {
    HistoryLongTermTicket ticket;
    @FXML
    private Label IdLabel;
    @FXML
    private Label NameLabel;
    @FXML
    private Label SurnameLabel;
    @FXML
    private Label BeginDateLabel;
    @FXML
    private Label DurationLabel;
    @FXML
    private Label CostLabel;
    @FXML
    private Button RefundButton;
    @FXML
    private VBox RefundButtonContainer;
    public HistoryLongTicketPane(HistoryLongTermTicket ticket) {
        this.ticket = ticket;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IdLabel.textProperty().setValue(String.valueOf(ticket.id()));
        NameLabel.textProperty().setValue(ticket.getPerson().getName());
        SurnameLabel.textProperty().setValue(ticket.getPerson().getSurname());
        BeginDateLabel.textProperty().setValue(ticket.startDate().format(DateTimeFormatter.ofPattern("HH:mm")));
        DurationLabel.textProperty().setValue(ticket.getLongTermTicketType().period().toString());
        CostLabel.textProperty().setValue(ticket.cost().toString());
    }
}
