package pl.tcs.oopproject.viewmodel.place;

import pl.tcs.oopproject.model.connection.MultiStopRoute;
import pl.tcs.oopproject.model.place.Place;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;

public class PlaceFactory {
	public static Place create(MultiStopRoute connection) {
		//CALL METHOD FINDING IN DATABASE SPECIFIC VACANT PLACES
		//IT SHOULD THROW EXCEPTIONS OR RETURN OBJECT TYPE PLACE
		//SAVE THIS OBJECT AS CHOSEN IN ACTIVE USER
		//RETURN THIS OBJECT
		Place place = null;
		ActiveUser.setPlace(place);
		return place;
	}
}
