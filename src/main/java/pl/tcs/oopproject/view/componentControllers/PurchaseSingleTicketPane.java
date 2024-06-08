package pl.tcs.oopproject.view.componentControllers;

import javafx.scene.layout.AnchorPane;
import pl.tcs.oopproject.model.ticket.SingleJourneyTrainTicket;

public class PurchaseSingleTicketPane extends AnchorPane {
    SingleJourneyTrainTicket ticket;
    public PurchaseSingleTicketPane(SingleJourneyTrainTicket ticket) {
        this.ticket = ticket;
    }
}
