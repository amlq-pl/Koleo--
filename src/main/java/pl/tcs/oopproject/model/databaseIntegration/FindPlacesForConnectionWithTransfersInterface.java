package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.place.TrainsAssignedSeats;

import java.sql.SQLException;
import java.util.List;

public interface FindPlacesForConnectionWithTransfersInterface {
	List<TrainsAssignedSeats> findPlacesForConnectionWithTransfers(MultiStopRoute multiStopRoute, int numOfPlaces) throws SQLException;
}
