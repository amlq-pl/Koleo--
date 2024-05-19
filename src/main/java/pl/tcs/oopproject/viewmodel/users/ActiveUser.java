package pl.tcs.oopproject.viewmodel.users;


import pl.tcs.oopproject.viewmodel.exception.IllegalOperationException;
import pl.tcs.oopproject.viewmodel.place.Place;

public class ActiveUser {
	private static Person activeUser; //active customer
	private static Place place; //temporarily viewed place
	
	public static void setActiveUser(Person person) {
		if(activeUser != null) throw new IllegalOperationException();
		activeUser = person;
	}
	
	public static void logOut() {
		activeUser = null;
	}
	
	public static Person getActiveUser() {
		return activeUser;
	}
	
	public static Place getPlace() {
		return place;
	}
	
	public static void setPlace(Place place) {
		ActiveUser.place = place;
	}
}
