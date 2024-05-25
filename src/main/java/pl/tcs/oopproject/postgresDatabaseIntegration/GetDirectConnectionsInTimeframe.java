package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.GetDirectConnectionsInTimeframeInterface;
import pl.tcs.oopproject.viewmodel.connection.DirectConnection;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GetDirectConnectionsInTimeframe implements GetDirectConnectionsInTimeframeInterface {
    @Override
    public ArrayList<DirectConnection> getDirectConnectionsInTimeframe(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }
}
