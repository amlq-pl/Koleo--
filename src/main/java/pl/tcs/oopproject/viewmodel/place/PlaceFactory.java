package pl.tcs.oopproject.viewmodel.place;

import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.place.TrainsAssignedSeats;
import pl.tcs.oopproject.postgresDatabaseIntegration.FindPlacesForConnectionWithTransfers;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceFactory {
	public static ArrayList<TrainsAssignedSeats> create(MultiStopRoute connection, int numberOfPlaces) throws SQLException {
		ArrayList<TrainsAssignedSeats> list = new FindPlacesForConnectionWithTransfers().findPlacesForConnectionWithTransfers(connection, numberOfPlaces);
		ActiveUser.setPlace(list);
			return list;
	}
}
		/*
		CALL METHOD FINDING IN DATABASE SPECIFIC VACANT PLACES
		IT SHOULD THROW EXCEPTIONS OR RETURN OBJECT TYPE PLACE
		SAVE THIS OBJECT AS CHOSEN IN ACTIVE USER
		RETURN THIS OBJECT
		IF TRAIN IN CONNECTION DOES NOT SUPPORT RESERVATION, THEN
		 RETURN TRAIN_WITHOUT_RESERVATION (singleton in AssignedSeat)
		*/