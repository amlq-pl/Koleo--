package pl.tcs.oopproject.viewmodel.users;


import pl.tcs.oopproject.model.place.Place;

public class ActiveUser {
	private static String activeUser; //active customer
	private static String login;
	private static Place place; //temporarily viewed place
	
	public static String getActiveUser() {
		return activeUser;
	}
	
	public static void setActiveUser(String user) {
		activeUser = user;
	}
	
	public static Place getPlace() {
		return place;
	}
	
	public static void setPlace(Place place) {
		ActiveUser.place = place;
	}
	
	public static boolean logOut() {
		if (activeUser == null) return false;
		activeUser = null;
		return true;
		
	}
}
