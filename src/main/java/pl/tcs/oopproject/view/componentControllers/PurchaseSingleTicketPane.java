package pl.tcs.oopproject.view.componentControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import pl.tcs.oopproject.App;
import pl.tcs.oopproject.model.ticket.SingleJourneyTrainTicket;

public class PurchaseSingleTicketPane extends AnchorPane {
    SingleJourneyTrainTicket ticket;
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
}
