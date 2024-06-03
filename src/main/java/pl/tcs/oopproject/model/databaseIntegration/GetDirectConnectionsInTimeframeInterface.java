package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.model.connection.ScheduledTrain;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface GetDirectConnectionsInTimeframeInterface {
	ArrayList<ScheduledTrain> getDirectConnectionsInTimeframe(LocalDateTime startDate, LocalDateTime endDate) throws SQLException;
}
