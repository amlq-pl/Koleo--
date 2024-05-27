package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.viewmodel.connection.ConnectionWithTransfers;
import pl.tcs.oopproject.viewmodel.place.Place;

import java.sql.SQLException;

public interface FindPlacesForConnectionWithTransfersInterface {
    Place findPlacesForConnectionWithTransfers(ConnectionWithTransfers connectionWithTransfers) throws SQLException;
}
