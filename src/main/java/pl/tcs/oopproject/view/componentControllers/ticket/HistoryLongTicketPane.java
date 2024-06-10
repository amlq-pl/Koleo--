package pl.tcs.oopproject.view.componentControllers.ticket;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.history.HistoryLongTermTicket;
import pl.tcs.oopproject.view.sceneControllers.AccountSceneController;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HistoryLongTicketPane extends AnchorPane implements Initializable {
    private final HistoryLongTermTicket ticket;
    private final AccountSceneController controller;
    public BooleanProperty isRefundable = new SimpleBooleanProperty();
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
    private Label CompanyLabel;
    @FXML
    private Label CostLabel;
    @FXML
    private Button RefundButton;
    @FXML
    private VBox RefundButtonContainer;

    public HistoryLongTicketPane(HistoryLongTermTicket ticket, AccountSceneController controller, boolean refundable) {
        this.ticket = ticket;
        this.controller = controller;
        isRefundable.setValue(refundable);
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
        BeginDateLabel.textProperty().setValue(ticket.startDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        DurationLabel.textProperty().setValue(ticket.longTermTicketType().period().toString());
        CompanyLabel.textProperty().setValue(ticket.longTermTicketType().company());
        CostLabel.textProperty().setValue(ticket.cost().toString());
        RefundButton.visibleProperty().bindBidirectional(isRefundable);

        RefundButton.setOnAction(c -> {
            try {
                controller.history.refundLongTermTicket(ticket);

                controller.reload();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
