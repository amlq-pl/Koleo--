package pl.tcs.oopproject.model.history;
import pl.tcs.oopproject.model.exception.*;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.postgresDatabaseIntegration.UserProfileUpdater;
import pl.tcs.oopproject.viewmodel.users.ActiveUser;
import pl.tcs.oopproject.model.users.Check;

import java.sql.SQLException;
import java.time.LocalDate;

public class UserProfileEditor {
	private final Person person = ActiveUser.getPerson();
	private final UserProfileUpdater updater;
	public UserProfileEditor() {
		updater = new UserProfileUpdater();
	}
	
	public void changeDateOfBirth(LocalDate date) throws SQLException {
		if(Check.incorrectDateOfBirth(date)) throw new InvalidDateOfBirthException();
		updater.updateDateOfBirth(ActiveUser.getActiveUser(), date);
		person.setDateOfBirth(date);
	}

	public void changeName(String name) throws SQLException {
		if(Check.incorrectName(name)) throw new InvalidNameOrSurnameException();
		updater.updateName(ActiveUser.getActiveUser(), name);
		person.setName(name);
	}
	
	public void changeSurname(String name) throws SQLException {
		if (Check.incorrectSurname(name)) throw new InvalidNameOrSurnameException();
		updater.updateSurname(ActiveUser.getActiveUser(), name);
		person.setSurname(name);
	}
	
	public void changeEmail(String email) throws SQLException {
		if (Check.incorrectEmail(email)) throw new InvalidEmailException();
		updater.updateEmail(ActiveUser.getActiveUser(), email);
		person.setEmailAddress(email);
	} // updateEmail must return boolean - if email is unique
	
	public void changeTelephoneNumber(String phone) throws SQLException {
		if(Check.incorrectTelephoneNumber(phone)) throw new InvalidTelephoneNumberException();
		updater.updateTelephoneNumber(ActiveUser.getActiveUser(), phone);
		person.setTelephoneNumber(phone);
	}
	
	public void changeLogin(String login) throws SQLException {
		if(updater.updateLogin(ActiveUser.getActiveUser(), login))
			ActiveUser.setActiveUser(login);
		else
			throw new ExistingUserException();
	}
	
	public void changePassword(String oldPassword, String newPassword) throws SQLException {
		if(!updater.updatePassword(ActiveUser.getActiveUser(), oldPassword, newPassword))
			throw new InvalidPasswordException();
	}
}
