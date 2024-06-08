package pl.tcs.oopproject.view.componentControllers;

import javafx.scene.layout.AnchorPane;
import pl.tcs.oopproject.model.history.HistoryLongTermTicket;

public class HistoryLongTicketPane extends AnchorPane {
    HistoryLongTermTicket ticket;
    public HistoryLongTicketPane(HistoryLongTermTicket ticket) {
        this.ticket = ticket;
    }
}
