package pl.tcs.oopproject.view.componentControllers;

import javafx.scene.layout.AnchorPane;
import pl.tcs.oopproject.model.ticket.LongTermTicketType;
import pl.tcs.oopproject.model.ticket.LongTermTrainTicket;

public class PurchaseLongTicketPane extends AnchorPane {
    LongTermTrainTicket ticket;
    public PurchaseLongTicketPane(LongTermTrainTicket ticket) {
        this.ticket = ticket;
    }
}
