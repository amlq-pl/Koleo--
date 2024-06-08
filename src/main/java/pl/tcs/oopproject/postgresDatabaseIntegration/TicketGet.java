package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.TicketGetter;
import pl.tcs.oopproject.model.history.HistoryLongTermTicket;
import pl.tcs.oopproject.model.history.HistorySingleJourneyTicket;

import java.util.ArrayList;

public class TicketGet implements TicketGetter {
    @Override
    public ArrayList<HistorySingleJourneyTicket> getSingleUseTickets(String login) {
        return null;
    }

    @Override
    public ArrayList<HistoryLongTermTicket> getLongTermTickets(String login) {
        return null;
    }
}
