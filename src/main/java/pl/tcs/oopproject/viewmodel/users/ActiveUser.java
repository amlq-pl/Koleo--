package pl.tcs.oopproject.viewmodel.users;


import pl.tcs.oopproject.model.assignedSeat.TrainsAssignedSeats;
import pl.tcs.oopproject.model.users.Person;

import java.util.ArrayList;

public class ActiveUser {
	private static String activeUser; //active customer, login
	private static Person person;
	private static ArrayList<TrainsAssignedSeats> trainsAssignedSeats; //temporarily viewed assignedSeat
	
	public static String getActiveUser() {
		return activeUser;
	}
	
	public static void setActiveUser(String user) {
		activeUser = user;
	}
	
	public static ArrayList<TrainsAssignedSeats> getPlace() {
		return trainsAssignedSeats;
	}
	
	public static void setPlace(ArrayList<TrainsAssignedSeats> trainsAssignedSeats) {
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
