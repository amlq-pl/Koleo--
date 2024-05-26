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

}
