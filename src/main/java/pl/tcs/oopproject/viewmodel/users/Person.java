package pl.tcs.oopproject.viewmodel.users;

import pl.tcs.oopproject.viewmodel.findconnection.KoleoException;

import java.time.LocalDate;

public class Person extends User {
	String name;
	String surname;
	LocalDate dateOfBirth;
	String emailAddress;
	String telephoneNumber;
	
	public Person(String name, String surname, LocalDate dateOfBirth, String emailAdress, String login, String password) throws KoleoException {
		super(login, password);
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.emailAddress = emailAdress;
		//TU NASTĘPUJE CHECK CZY DANE OSOBOWE SĄ POPRAWNE ORAZ WYWOŁANIE ZAPISU DANEJ OSOBY DO BAZY
	}
	public Person(String name, String surname, LocalDate dateOfBirth, String emailAddress, String telephoneNumber, String login, String password) throws KoleoException{
		this(name, surname, dateOfBirth, emailAddress, login, password);
		this.telephoneNumber = telephoneNumber;
	}
	
	
	
}
