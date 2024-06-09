package pl.tcs.oopproject.viewmodel.place;

import pl.tcs.oopproject.model.assignedSeat.TrainsAssignedSeats;
import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.exception.AllTrainsAlreadyDepartedException;
import pl.tcs.oopproject.postgresDatabaseIntegration.FindPlacesForConnectionWithTransfers;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PlaceFactory {
    public static ArrayList<TrainsAssignedSeats> create(MultiStopRoute connection, int numberOfPlaces) throws SQLException {
        LocalDateTime departureDate = connection.departureTime();
        if (departureDate.isBefore(LocalDateTime.now())) throw new AllTrainsAlreadyDepartedException();
        ArrayList<TrainsAssignedSeats> list = new FindPlacesForConnectionWithTransfers().findPlacesForConnectionWithTransfers(connection, numberOfPlaces);
        ActiveUser.setPlace(list);
        return list;
    }
}
