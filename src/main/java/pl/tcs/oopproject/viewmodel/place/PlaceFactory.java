package pl.tcs.oopproject.viewmodel.place;

import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.place.TrainsAssignedSeats;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

public class PlaceFactory {
	public static TrainsAssignedSeats create(MultiStopRoute connection) {
		/*
		CALL METHOD FINDING IN DATABASE SPECIFIC VACANT PLACES
		IT SHOULD THROW EXCEPTIONS OR RETURN OBJECT TYPE PLACE
		SAVE THIS OBJECT AS CHOSEN IN ACTIVE USER
		RETURN THIS OBJECT
		IF TRAIN IN CONNECTION DOES NOT SUPPORT RESERVATION, THEN
		 RETURN TRAIN_WITHOUT_RESERVATION (singleton in AssignedSeat)
		*/
		TrainsAssignedSeats trainsAssignedSeats = null;
		ActiveUser.setPlace(trainsAssignedSeats);
		return trainsAssignedSeats;
	}
}
