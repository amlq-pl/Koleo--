package pl.tcs.oopproject.model.history;
import pl.tcs.oopproject.model.exception.InvalidDateOfBirthException;
import pl.tcs.oopproject.model.exception.InvalidEmailException;
import pl.tcs.oopproject.model.exception.InvalidNameOrSurnameException;
import pl.tcs.oopproject.model.exception.InvalidTelephoneNumberException;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;
import pl.tcs.oopproject.viewmodel.users.Check;

import java.time.LocalDate;

public class UserProfileEditor {
	private final Person person = ActiveUser.getPerson();
	public UserProfileEditor() {}
	
	public void changeDateOfBirth(LocalDate date) {
		if(!Check.correctDateOfBirth(date)) throw new InvalidDateOfBirthException();
		//SAVE CHANGES IN DATABASE (Person, date)
		person.setDateOfBirth(date);
	}

	public void changeName(String name) {
		if(Check.incorrectName(name)) throw new InvalidNameOrSurnameException();
		//SAVE CHANGES IN DATABASE (Person, name)
		person.setName(name);
	}
	
	public void changeSurname(String name) {
		if(Check.incorrectSurname(name)) throw new InvalidNameOrSurnameException();
		//SAVE CHANGES IN DATABASE (Person, surname)
		person.setSurname(name);
	}
	
	public void changeEmail(String email) {
		if(Check.incorrectEmail(email)) throw new InvalidEmailException();
		//SAVE CHANGES IN DATABASE (Person, email)
		person.setEmailAddress(email);
	}
	
	public void changeTelephoneNumber(String phone) {
		if(Check.incorrectTelephoneNumber(phone)) throw new InvalidTelephoneNumberException();
		//SAVA CHANGES IN DATABASE (Person, phone)
		person.setTelephoneNumber(phone);
	}
	
	public void changeLogin(String login) {
		//CHECK IF DATA CAN BE CHANGE IN DATABASE
		//IF NOT - EXCEPTION
		//ELSE - CHANGE IT
	}
	
	public void changePassword(String oldPassword, String newPassword) {
		//CHECK IF OLD PASSWORD IS CORRECT
		//IF YES - TRY CHANGING
		//ELSE - EXCEPTION
	}
}
