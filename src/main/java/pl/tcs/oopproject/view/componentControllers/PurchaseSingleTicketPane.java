package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.ticket.SingleJourneyTrainTicket;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PurchaseSingleTicketPane extends AnchorPane implements Initializable {
    SingleJourneyTrainTicket ticket;
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
    private Label ArrHourLabel;
    @FXML
    private Label CostLabel;
    @FXML
    private Label DiscountLabel;
    @FXML
    private Label VoucherLabel;
    public PurchaseSingleTicketPane(SingleJourneyTrainTicket ticket) {
        this.ticket = ticket;

        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/purchase-single-ticket-pane.fxml"));
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
        NameLabel.textProperty().setValue(ticket.getPerson().getName());
        SurnameLabel.textProperty().setValue(ticket.getPerson().getSurname());
        DepStationLabel.textProperty().setValue(ticket.departureStation().town());
        DepHourLabel.textProperty().setValue(ticket.departureTime()
                .format(DateTimeFormatter.ofPattern("HH:mm")));
        ArrStationLabel.textProperty().setValue(ticket.arrivalStation().town());
        ArrHourLabel.textProperty().setValue(ticket.arrivalTime()
                .format(DateTimeFormatter.ofPattern("HH:mm")));
        try {
            CostLabel.textProperty().setValue(ticket.cost().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DiscountLabel.textProperty().setValue(ticket.appliedDiscount().discount());
        VoucherLabel.textProperty().setValue(ticket.appliedVoucher().discount());
    }
}
