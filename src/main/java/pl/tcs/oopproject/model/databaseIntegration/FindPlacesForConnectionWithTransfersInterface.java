package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.model.assignedSeat.TrainsAssignedSeats;
import pl.tcs.oopproject.model.connection.MultiStopRoute;

import java.sql.SQLException;
import java.util.List;

public interface FindPlacesForConnectionWithTransfersInterface {
    List<TrainsAssignedSeats> findPlacesForConnectionWithTransfers(MultiStopRoute multiStopRoute, int numOfPlaces) throws SQLException;
}
