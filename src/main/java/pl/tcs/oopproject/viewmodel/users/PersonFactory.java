package pl.tcs.oopproject.viewmodel.users;

import pl.tcs.oopproject.database.AuthenticateLogin;
import pl.tcs.oopproject.viewmodel.exception.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import java.util.regex.Pattern;

public class PersonFactory {
	
	private static boolean correctEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
				"[a-zA-Z0-9_+&*-]+)*@" +
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" +
				"A-Z]{2,7}$";
		Pattern patern = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return patern.matcher(email).matches();
	}
	
	private static boolean correctTelephoneNumber(String number) {
		if(number == null) return true;
		number = number.replaceAll("\\s", "");
		String numberRegex = "^\\+\\d{10,12}$";
		Pattern pattern = Pattern.compile(numberRegex);
		return pattern.matcher(number).matches();
		
	} //format : +48123456789, +48 123 456 789, the important stuff : international prefix if necessary
	
	public Person logIn(String login, String password) throws KoleoException {
		Person person = AuthenticateLogin.authenticate(login, password);
		if (person == null) throw new InvalidUsernameOrPasswordException();
		return person;
	} //log in
	
	public Person create(String name, String surname, LocalDate dateOfBirth, String email) throws KoleoException {
		if (name.length() > 20 || name.length() < 2) throw new InvalidNameOrSurnameException();
		if (Period.between(dateOfBirth, LocalDate.now()).getYears() < 12) throw new InvalidNameOrSurnameException();
		if (!correctEmail(email)) throw new InvalidEmailException();
		return new Person(name, surname, dateOfBirth, email, null);
	} //buy without signing up nor logging in and without phone number
	
	public Person create(String name, String surname, LocalDate dateOfBirth, String email, String phoneNumber) throws KoleoException {
		if (!correctTelephoneNumber(phoneNumber)) throw new InvalidTelephoneNumberException();
		return create(name, surname, dateOfBirth, email);
	} //buy without signing up nor logging in

	public Person create(String name, String surname, LocalDate dateOfBirth, String email,String login, String password) throws KoleoException{
		//CODE HERE: CHECK IF USER EXISTS IN DATABASE - SOME KIND OF STATIC METHOD
		
		Person person = create(name, surname, dateOfBirth, email);
		//CODE HERE: SAVE IN DATABASE
		
		return person;
		
	} //sign up without phonenumber
}
