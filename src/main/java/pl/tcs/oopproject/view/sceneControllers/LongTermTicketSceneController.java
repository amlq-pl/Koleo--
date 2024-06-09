package pl.tcs.oopproject.view.sceneControllers;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.discount.Discount;
import pl.tcs.oopproject.model.discount.Voucher;
import pl.tcs.oopproject.model.ticket.LongTermTicketType;
import pl.tcs.oopproject.model.ticket.LongTermTrainTicket;
import pl.tcs.oopproject.viewmodel.tickets.TicketFactory;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LongTermTicketSceneController implements Initializable {

    public Button BuyButton;
    public ComboBox<LongTermTicketType> TicketCombo;
    public DatePicker StartDate;
    public ComboBox<Discount> DiscountCombo;
    public ComboBox<Voucher> VoucherCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TicketCombo.setItems(FXCollections.observableArrayList(App.LongTermTicketTypes));
        DiscountCombo.setItems(FXCollections.observableArrayList(App.Discounts));
        DiscountCombo.valueProperty().setValue(App.DEFAULT_DISCOUNT);
        VoucherCombo.setItems(FXCollections.observableArrayList(App.Vouchers));
        VoucherCombo.valueProperty().setValue(App.DEFAULT_VOUCHER);

        BuyButton.setOnAction(c -> {
            TicketFactory factory = new TicketFactory();
            try {
                factory.createLongTermTicket(TicketCombo.getValue(), DiscountCombo.getValue(), VoucherCombo.getValue(), StartDate.getValue(), ActiveUser.getPerson());
                Stage thisStage = (Stage) StartDate.getScene().getWindow();
                thisStage.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
