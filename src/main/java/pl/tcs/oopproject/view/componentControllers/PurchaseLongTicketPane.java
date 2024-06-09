package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.ticket.LongTermTrainTicket;

public class PurchaseLongTicketPane extends AnchorPane {
    LongTermTrainTicket ticket;

    public PurchaseLongTicketPane(LongTermTrainTicket ticket) {
        this.ticket = ticket;

        FXMLLoader loader = new FXMLLoader(App.class.getResource("components/purchase-long-ticket-pane.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
