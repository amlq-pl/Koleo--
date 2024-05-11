package pl.tcs.oopproject.viewmodel.users;

import pl.tcs.oopproject.model.AuthenticateLogin;
import pl.tcs.oopproject.model.Checkers;
import pl.tcs.oopproject.model.InsertNewPersonToDatabase;
import pl.tcs.oopproject.viewmodel.exception.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.regex.Pattern;

public class PersonFactory {
	
	private static boolean correctEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
				"[a-zA-Z0-9_+&*-]+)*@" +
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" +
				"A-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pattern.matcher(email).matches();
	}
	
	private static boolean correctTelephoneNumber(String number) {
		if(number == null) return true;
		number = number.replaceAll("\\s", "");
		String numberRegex = "^\\+\\d{10,12}$";
		Pattern pattern = Pattern.compile(numberRegex);
		return pattern.matcher(number).matches();
		
	} //format : +48123456789, +48 123 456 789, the important stuff : international prefix if necessary
	
	public Person logIn(String login, String password) throws KoleoException {
		try {
			Person person =  AuthenticateLogin.authenticate(login, password);
			ActiveUser.setActiveUser(person);
			return person;
		}
		catch (SQLException e) {
			throw new InvalidUsernameOrPasswordException();
		}
		
	} //log in
	
	public Person create(String name, String surname, LocalDate dateOfBirth, String email, String phoneNumber) throws KoleoException {
		if(Objects.equals(phoneNumber, ""))  phoneNumber = null;
		if (!correctTelephoneNumber(phoneNumber)) throw new InvalidTelephoneNumberException();
		if (name.length() > 20 || name.length() < 2) throw new InvalidNameOrSurnameException();
		if (Period.between(dateOfBirth, LocalDate.now()).getYears() < 12) throw new InvalidDateOfBirthException();
		if (!correctEmail(email)) throw new InvalidEmailException();
		return new Person(name, surname, dateOfBirth, email, phoneNumber);
	} //buy without signing up nor logging in

	
	public Person create(String name, String surname, LocalDate dateOfBirth, String email, String phoneNumber,String login, String password) throws KoleoException {
		Person person = create(name, surname, dateOfBirth, email, phoneNumber);
		try {
			if (Checkers.checkIfUserExists(login)) throw new ExistingUserException();
			if(!InsertNewPersonToDatabase.insert(person, login, password)) throw new SQLException();
			ActiveUser.setActiveUser(person);
			return person;
		}
		catch (SQLException e) {
			throw new InternalDatabaseException();
		}
	} //sign up without phoneNumber
}
