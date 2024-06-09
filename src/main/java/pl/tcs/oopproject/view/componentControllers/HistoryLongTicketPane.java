package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import pl.tcs.oopproject.App;
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

        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/history-long-ticket-pane.fxml"));
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
        BeginDateLabel.textProperty().setValue(ticket.startDate().format(DateTimeFormatter.ofPattern("HH:mm")));
        DurationLabel.textProperty().setValue(ticket.longTermTicketType().period().toString());
        CostLabel.textProperty().setValue(ticket.cost().toString());
    }
}
