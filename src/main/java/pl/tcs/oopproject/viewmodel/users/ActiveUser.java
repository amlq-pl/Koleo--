package pl.tcs.oopproject.viewmodel.users;

public class ActiveUser {
	public static Person activeUser;
	
	public static void setActiveUser(Person person) {
		activeUser = person;
	}
	
	public static Person getActiveUser(Person person) {
		return activeUser;
	}
}
