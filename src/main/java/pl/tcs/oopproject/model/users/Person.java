package pl.tcs.oopproject.model.users;


import pl.tcs.oopproject.model.exception.KoleoException;

import java.time.LocalDate;

public class Person {
	private String name;
	private String surname;
	private LocalDate dateOfBirth;
	private String emailAddress;
	private String telephoneNumber;
	
	public Person(String name, String surname, LocalDate dateOfBirth, String emailAddress, String telephoneNumber) throws KoleoException {
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
		this.emailAddress = emailAddress;
		this.telephoneNumber = telephoneNumber;
	}
	
	public String getName() {
		if (name == null) return "";
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		if (surname == null) return "";
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	
	public void display() {
		System.out.println("Name and Surname: " + name + " " + surname);
		System.out.println("Date of Birth: " + dateOfBirth);
		System.out.println("Email Address: " + emailAddress);
		if (telephoneNumber != null)
			System.out.println("Phone Number: " + telephoneNumber);
	}
}
