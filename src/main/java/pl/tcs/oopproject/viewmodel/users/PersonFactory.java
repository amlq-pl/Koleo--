package pl.tcs.oopproject.viewmodel.users;

import pl.tcs.oopproject.model.exception.*;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.postgresDatabaseIntegration.AuthenticateLogin;
import pl.tcs.oopproject.postgresDatabaseIntegration.Checkers;
import pl.tcs.oopproject.postgresDatabaseIntegration.InsertNewPersonToDatabase;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.regex.Pattern;

public class PersonFactory {
	static int minNameLength = 2;
	static int maxNameLength = 20;
	static int minAge = 12;
	
	public static Person logIn(String login, String password) throws KoleoException {
		try {
			AuthenticateLogin authenticator = new AuthenticateLogin();
			Person person = authenticator.authenticate(login, password);
			ActiveUser.setActiveUser(login);
			return person;
		} catch (SQLException e) {
			throw new InvalidUsernameOrPasswordException();
		}
		
	} //log in
	
	public static Person create(String name, String surname, LocalDate dateOfBirth, String email, String phoneNumber) throws KoleoException {
		if (Objects.equals(phoneNumber, "")) phoneNumber = null;
		if (!Check.correctTelephoneNumber(phoneNumber)) throw new InvalidTelephoneNumberException();
		if (name.length() > maxNameLength || name.length() < minNameLength) throw new InvalidNameOrSurnameException();
		if (Period.between(dateOfBirth, LocalDate.now()).getYears() < minAge) throw new InvalidDateOfBirthException();
		if (!Check.correctEmail(email)) throw new InvalidEmailException();
		return new Person(name, surname, dateOfBirth, email, phoneNumber);
	} //buy without signing up nor logging in
	
	
	public static Person create(String name, String surname, LocalDate dateOfBirth, String email, String phoneNumber, String login, String password) throws KoleoException {
		InsertNewPersonToDatabase inserter = new InsertNewPersonToDatabase();
		Person person = create(name, surname, dateOfBirth, email, phoneNumber);
		try {
			Checkers checker = new Checkers();
			if (checker.checkIfUserExists(login)) throw new ExistingUserException();
			if (!inserter.insert(person, login, password)) throw new SQLException();
			ActiveUser.setActiveUser(login);
			return person;
		} catch (SQLException e) {
			throw new InternalDatabaseException();
		}
	} //sign up without phoneNumber
}
