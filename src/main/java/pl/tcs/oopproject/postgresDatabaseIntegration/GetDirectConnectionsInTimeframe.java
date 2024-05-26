package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.GetDirectConnectionsInTimeframeInterface;
import pl.tcs.oopproject.viewmodel.connection.DirectConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GetDirectConnectionsInTimeframe implements GetDirectConnectionsInTimeframeInterface {
    @Override
    public ArrayList<DirectConnection> getDirectConnectionsInTimeframe(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select * from stations s " +
                "where s.czas_przyjazdu>=?::timestamp and s.czas_przyjazdu<=?::timestamp ;");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        ps.setString(1,formatter.format(startDate));
        ps.setString(2,formatter.format(endDate));
        ResultSet rs = ps.executeQuery();


        int curr_id_przejazdu,prev_id_przejazdu=-1;

        while (rs.next()) {
            curr_id_przejazdu = rs.getInt("id_przejazdu");
            if(curr_id_przejazdu!=prev_id_przejazdu) {

            }
        }

        ArrayList<DirectConnection> connections = new ArrayList<>();
        return null;
    }
}
