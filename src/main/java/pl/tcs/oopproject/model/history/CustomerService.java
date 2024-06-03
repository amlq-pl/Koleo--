package pl.tcs.oopproject.model.history;

import pl.tcs.oopproject.model.users.Person;

public class CustomerService {
	private String email;
	private Person person;
	public CustomerService() {}
	
	private void setData(){
		//SET DATA
		email = null;
		person = null;
	}
	
	public Person getPerson() {
		if(person == null) setData();
		return person;
	}
	
	public String getEmail(){
		if(email == null) setData();
		return email;
	}
}
