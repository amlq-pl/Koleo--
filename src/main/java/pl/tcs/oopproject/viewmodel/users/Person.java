package pl.tcs.oopproject.viewmodel.users;


import pl.tcs.oopproject.viewmodel.exception.KoleoException;

import java.time.LocalDate;

public class Person {
	private String name;
	private String surname;
	private LocalDate dateOfBirth;
	private String emailAddress;
	private String telephoneNumber = null;
	
	public Person(String name, String surname, LocalDate dateOfBirth, String emailAddress, String telephoneNumber) throws KoleoException{
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.emailAddress = emailAddress;
		this.telephoneNumber = telephoneNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
