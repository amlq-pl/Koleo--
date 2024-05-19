package pl.tcs.oopproject.viewmodel.users;
import pl.tcs.oopproject.viewmodel.findconnection.KoleoException;

public class User {
	private String login;
	private String password;
	
	User(String login, String password) throws KoleoException {
		this.login = login;
		this.password = password;
	}
	
	public String getLogin() {
		return login;
	}
	
	public String getPassword() {
		return password;
	}
}
