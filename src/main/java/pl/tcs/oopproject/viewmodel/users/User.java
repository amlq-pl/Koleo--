package pl.tcs.oopproject.viewmodel.users;

import pl.tcs.oopproject.viewmodel.findconnection.KoleoException;

public class User {
	String login;
	String password;
	
	User(String login, String password) throws KoleoException {
		this.login = login;
		//jakieś szyfrowanie hasła
	}
	
}
