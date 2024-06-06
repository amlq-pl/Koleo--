package pl.tcs.oopproject.viewmodel.users;


import pl.tcs.oopproject.model.place.TrainsAssignedSeats;
import pl.tcs.oopproject.model.users.Person;

public class ActiveUser {
	private static String activeUser; //active customer, login
	private static Person person;
	private static TrainsAssignedSeats trainsAssignedSeats; //temporarily viewed place
	
	public static String getActiveUser() {
		return activeUser;
	}
	
	public static void setActiveUser(String user) {
		activeUser = user;
	}
	
	public static TrainsAssignedSeats getPlace() {
		return trainsAssignedSeats;
	}
	
	public static void setPlace(TrainsAssignedSeats trainsAssignedSeats) {
		ActiveUser.trainsAssignedSeats = trainsAssignedSeats;
	}
	
	public static boolean logOut() {
		if (activeUser == null) return false;
		activeUser = null;
		return true;
		
	}
	
	public static Person getPerson() {
		return person;
	}
	
	public static void setPerson(Person person) {
		ActiveUser.person = person;
	}
}
