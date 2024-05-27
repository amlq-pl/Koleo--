package pl.tcs.oopproject.viewmodel.users;


import pl.tcs.oopproject.model.place.Place;
import pl.tcs.oopproject.model.users.Person;

public class ActiveUser {
	private static Person activeUser; //active customer
	private static Place place; //temporarily viewed place
	
	public static Person getActiveUser() {
		return activeUser;
	}
	
	public static void setActiveUser(Person person) {
		activeUser = person;
	}
	
	public static Place getPlace() {
		return place;
	}
	
	public static void setPlace(Place place) {
		ActiveUser.place = place;
	}
}
