package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.place.TrainsAssignedSeats;

import java.sql.SQLException;

public interface FindPlacesForConnectionWithTransfersInterface {
	TrainsAssignedSeats findPlacesForConnectionWithTransfers(MultiStopRoute multiStopRoute) throws SQLException;
}
