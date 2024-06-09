package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.connection.ReservationType;
import pl.tcs.oopproject.model.connection.RouteStops;
import pl.tcs.oopproject.model.connection.ScheduledTrain;
import pl.tcs.oopproject.model.databaseIntegration.GetDirectConnectionsInTimeframeInterface;
import pl.tcs.oopproject.model.station.Station;
import pl.tcs.oopproject.viewmodel.connection.DirectConnectionBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GetDirectConnectionsInTimeframe implements GetDirectConnectionsInTimeframeInterface {
    @Override
    public ArrayList<ScheduledTrain> getDirectConnectionsInTimeframe(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select * from stations s " +
                "where s.czas_przyjazdu>=?::timestamp and s.czas_przyjazdu<=?::timestamp ;");

        PreparedStatement przejazdy = DB.connection.prepareStatement("select p.koszt_bazowy,p.czy_rezerwacja_miejsc,pr.nazwa_skrocona,count(sp.numer_stacji) as ile_stacji from przejazdy p " +
                "join trasy_przewoznicy tp on p.id_trasy_przewoznika = tp.id_trasy_przewoznika " +
                "join przewoznicy pr on tp.id_przewoznika = pr.id_przewoznika " +
                "join trasy on tp.id_trasy = trasy.id_trasy" +
                " join stacje_posrednie sp on trasy.id_trasy = sp.id_trasy " +
                "where p.id_przejazdu=? " +
                "group by sp.id_trasy, p.koszt_bazowy, p.czy_rezerwacja_miejsc, pr.nazwa_skrocona;");
        ResultSet przejazdyResult = null;


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        ps.setString(1, formatter.format(startDate));
        ps.setString(2, formatter.format(endDate));
        ResultSet rs = ps.executeQuery();

        ArrayList<ScheduledTrain> connections = new ArrayList<>();
        int currIdPrzejazdu, prevIdPrzejazdu = -1;

        RouteStops routeStops = null;
        DirectConnectionBuilder builder = new DirectConnectionBuilder();
        int liczbaStacji = 1;
        while (rs.next()) {
            currIdPrzejazdu = rs.getInt("id_przejazdu");

            if (currIdPrzejazdu != prevIdPrzejazdu) {
                if (routeStops != null && routeStops.numberOfStops() > 1) {
                    builder.addCost(-przejazdyResult.getDouble("koszt_bazowy") / (liczbaStacji - 1));
                    builder.setConnection(routeStops);
                    connections.add(builder.getTrainConnection());
                }

                przejazdy.setInt(1, currIdPrzejazdu);
                przejazdyResult = przejazdy.executeQuery();
                przejazdyResult.next();

                routeStops = new RouteStops(new ArrayList<>());
                builder = new DirectConnectionBuilder();

                builder.setNumber(currIdPrzejazdu);

                builder.setTrainType(przejazdyResult.getBoolean("czy_rezerwacja_miejsc") ?
                        ReservationType.WITH_RESERVATION : ReservationType.WITHOUT_RESERVATION);

                builder.setCompany(przejazdyResult.getString("nazwa_skrocona"));

                liczbaStacji = przejazdyResult.getInt("ile_stacji");
            }

            builder.addCost(przejazdyResult.getDouble("koszt_bazowy") / (liczbaStacji - 1));


            routeStops.add(new Station(rs.getString("nazwa_stacji"),
                    rs.getTimestamp("czas_odjazdu").toLocalDateTime(),
                    rs.getTimestamp("czas_przyjazdu").toLocalDateTime()));

            prevIdPrzejazdu = currIdPrzejazdu;
        }
        if (routeStops.numberOfStops() > 1) {
            builder.addCost(-przejazdyResult.getDouble("koszt_bazowy") / (liczbaStacji - 1));
            builder.setConnection(routeStops);
            connections.add(builder.getTrainConnection());
        }

        return connections;
    }
}
