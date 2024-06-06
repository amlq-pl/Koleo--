package pl.tcs.oopproject.model.history;

import pl.tcs.oopproject.model.users.Person;

import java.sql.SQLException;

public class CustomerService {
	static int minNameLength = 2;
	static int maxNameLength = 20;
	static int minAge = 12;
	private String email;
	private Person person;
	public CustomerService() {}
	
	private void setData() throws  SQLException{
		//SET DATA FROM DATABASE USING login from ACTIVE USER
		email = null;
		person = null;
	}
	
	public Person getPerson() throws SQLException {
		if(person == null) setData();
		return person;
	}
	
	public String getEmail() throws SQLException {
		if(email == null) setData();
		return email;
	}
	public void change(String name) {
		
	}
}
