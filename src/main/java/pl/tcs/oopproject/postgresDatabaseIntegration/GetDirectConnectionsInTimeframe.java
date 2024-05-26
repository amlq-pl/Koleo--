package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.GetDirectConnectionsInTimeframeInterface;
import pl.tcs.oopproject.viewmodel.DirectConnectionBuilder;
import pl.tcs.oopproject.viewmodel.connection.DirectConnection;
import pl.tcs.oopproject.viewmodel.connection.TrainConnection;
import pl.tcs.oopproject.viewmodel.connection.TrainIsReservation;
import pl.tcs.oopproject.viewmodel.station.Station;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GetDirectConnectionsInTimeframe implements GetDirectConnectionsInTimeframeInterface {
    @Override
    public ArrayList<DirectConnection> getDirectConnectionsInTimeframe(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select * from stations s " +
                "where s.czas_przyjazdu>=?::timestamp and s.czas_przyjazdu<=?::timestamp ;");

        PreparedStatement przejazdy = DB.connection.prepareStatement("select * from przejazdy p " +
                "join trasy_przewoznicy tp on p.id_trasy_przewoznika = tp.id_trasy_przewoznika " +
                "join przewoznicy on tp.id_przewoznika = przewoznicy.id_przewoznika " +
                "join trasy on tp.id_trasy = trasy.id_trasy where p.id_przejazdu=?");
        ResultSet przejazdyResult = null;


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        ps.setString(1, formatter.format(startDate));
        ps.setString(2, formatter.format(endDate));
        ResultSet rs = ps.executeQuery();

        ArrayList<DirectConnection> connections = new ArrayList<>();
        int currIdPrzejazdu, prevIdPrzejazdu = -1;

        TrainConnection trainConnection = null;
        DirectConnectionBuilder builder = new DirectConnectionBuilder();
        int liczbaStacji = 1;
        while (rs.next()) {
            currIdPrzejazdu = rs.getInt("id_przejazdu");

            if (currIdPrzejazdu != prevIdPrzejazdu) {
                if (trainConnection != null && trainConnection.getSize() > 1) {
                    builder.addCost(-przejazdyResult.getDouble("koszt_bazowy") / (liczbaStacji - 1));
                    builder.setConnection(trainConnection);
                    connections.add(builder.getTrainConnection());
                }

                przejazdy.setInt(1, currIdPrzejazdu);
                przejazdyResult = przejazdy.executeQuery();
                przejazdyResult.next();

                trainConnection = new TrainConnection(new ArrayList<>());
                builder = new DirectConnectionBuilder();

                builder.setNumber(currIdPrzejazdu);

                builder.setTrainType(przejazdyResult.getBoolean("czy_rezerwacja_miejsc") ?
                        TrainIsReservation.WITH_RESERVATION : TrainIsReservation.WITHOUT_RESERVATION);

                builder.setCompany(przejazdyResult.getString("nazwa_skrocona"));

                liczbaStacji = przejazdyResult.getInt("ile_stacji");
            }

            builder.addCost(przejazdyResult.getDouble("koszt_bazowy") / (liczbaStacji - 1));


            trainConnection.add(new Station(rs.getString("nazwa_stacji"),
                    rs.getTimestamp("czas_odjazdu").toLocalDateTime(),
                    rs.getTimestamp("czas_przyjazdu").toLocalDateTime()));

            prevIdPrzejazdu = currIdPrzejazdu;
        }
        if (trainConnection.getSize() > 1) {
            builder.addCost(-przejazdyResult.getDouble("koszt_bazowy") / (liczbaStacji - 1));
            builder.setConnection(trainConnection);
            connections.add(builder.getTrainConnection());
        }

        return connections;
    }
}
