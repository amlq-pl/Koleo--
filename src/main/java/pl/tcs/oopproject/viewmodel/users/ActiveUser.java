package pl.tcs.oopproject.viewmodel.users;


import pl.tcs.oopproject.model.place.TrainsAssignedSeats;

public class ActiveUser {
	private static String activeUser; //active customer
	private static String login;
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
}
