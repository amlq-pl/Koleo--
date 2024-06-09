package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.model.history.HistoryLongTermTicket;
import pl.tcs.oopproject.model.history.HistorySingleJourneyTicket;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TicketGetter {
    /**
     * returns list of all tickets bought by the user login
     */
    ArrayList<HistorySingleJourneyTicket> getSingleUseTickets(String login) throws SQLException;

    ArrayList<HistoryLongTermTicket> getLongTermTickets(String login) throws SQLException;

}
