package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.viewmodel.connection.DirectConnection;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface GetDirectConnectionsInTimeframeInterface {
    ArrayList<DirectConnection> getDirectConnectionsInTimeframe(LocalDateTime startDate, LocalDateTime endDate) throws SQLException;
}
