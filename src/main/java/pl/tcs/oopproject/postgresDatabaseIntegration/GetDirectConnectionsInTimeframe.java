package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.GetDirectConnectionsInTimeframeInterface;
import pl.tcs.oopproject.viewmodel.connection.DirectConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class GetDirectConnectionsInTimeframe implements GetDirectConnectionsInTimeframeInterface {
    @Override
    public ArrayList<DirectConnection> getDirectConnectionsInTimeframe(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select * from stations s where s.czas_przyjazdu>=? and s.czas_przyjazdu<=?;");
        ArrayList<DirectConnection> connections = new ArrayList<>();
        return null;
    }
}
