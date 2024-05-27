package pl.tcs.oopproject.postgresDatabaseIntegration;

import pl.tcs.oopproject.model.databaseIntegration.FindPlacesForConnectionWithTransfersInterface;
import pl.tcs.oopproject.viewmodel.connection.ConnectionWithTransfers;
import pl.tcs.oopproject.viewmodel.connection.DirectConnection;
import pl.tcs.oopproject.viewmodel.place.Place;
import pl.tcs.oopproject.viewmodel.place.SpecificSeat;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FindPlacesForConnectionWithTransfers implements FindPlacesForConnectionWithTransfersInterface {
    @Override
    public Place findPlacesForConnectionWithTransfers(ConnectionWithTransfers connectionWithTransfers) throws SQLException {
        ArrayList<SpecificSeat> places = new ArrayList<>();
        for (DirectConnection directConnection : connectionWithTransfers.getTrains()) {
            places.add(findSpecificSeat(directConnection));
        }
        return new Place(connectionWithTransfers, places);
    }

    private SpecificSeat findSpecificSeat(DirectConnection directConnection) throws SQLException {
        int startStation=getNumOfStation(directConnection.getNumber(),directConnection.getFirstStation().getTown()),
                endStation=getNumOfStation(directConnection.getNumber(),directConnection.getLastStation().getTown());


        int numOfNeededDifferentCarriageConfigurationsInConnection=getNumOfIntersectingCarriageChangesInConnection(directConnection.getNumber(), startStation, endStation);
        for(int inspectedCarriage=1;inspectedCarriage<=getNumOfMaxCarriages(directConnection.getNumber());inspectedCarriage++){

        }
        return null;
    }

    private int getNumOfStation(int idConnection, String stationName) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select getnumofstation(?,?)");
        ps.setInt(1, idConnection);
        ps.setString(2, stationName);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    private int getNumOfMaxCarriages(int idConnection) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select max(psc.nr_wagonu) from przejazdy p " +
                "join przejazdy_sklad ps on p.id_przejazdu = ps.id_przejazdu " +
                "join przejazdy_sklad_czesci psc on psc.id_przejazdu_skladu = ps.id_przejazdu_skladu "+
                "where p.id_przejazdu = ?");
        ps.setInt(1, idConnection);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }


    //TODO to nie dziala xd
    private int getNumOfIntersectingCarriageChangesInConnection(int idConnection, int startStation, int endStation) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select count(*) from przejazdy p join przejazdy_sklad ps on p.id_przejazdu = ps.id_przejazdu " +
                "where p.id_przejazdu = ? and ( ? < ps.od_stacji and ps.od_stacji < ?  or ( ? < ps.do_stacji and ps.do_stacji < ? )) ");
        ps.setInt(1, idConnection);
        ps.setInt(2, startStation);
        ps.setInt(3, endStation);
        ps.setInt(4, startStation);
        ps.setInt(5, endStation);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    //TODO to tez chyba nie ale ide spac xd
    private boolean isCarriageCorrect(int connectionId, int carriageNum, int needed) throws SQLException {
        PreparedStatement ps = DB.connection.prepareStatement("select count(*) from przejazdy p " +
                "join przejazdy_sklad ps on p.id_przejazdu = ps.id_przejazdu " +
                "join przejazdy_sklad_czesci psc on ps.id_przejazdu_skladu = psc.id_przejazdu_skladu " +
                "where p.id_przejazdu = ? and psc.nr_wagonu = ? ");
        return false;
    }

}
