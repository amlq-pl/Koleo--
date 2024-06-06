package pl.tcs.oopproject.model.databaseIntegration;

import pl.tcs.oopproject.model.exception.InternalDatabaseException;
import pl.tcs.oopproject.model.users.Person;

import java.time.LocalDate;

public interface UpdateUserProfile {
	void updateName(String login, String name) throws InternalDatabaseException;
	void updateSurname(String login, String name) throws InternalDatabaseException;
	void updateEmail(String login, String email) throws InternalDatabaseException;
	void updateTelephoneNumber(String login, String phone) throws InternalDatabaseException;
	void updateDateOfBirth(String login, LocalDate date) throws InternalDatabaseException;
	boolean updateLogin(String oldLogin, String newLogin) throws InternalDatabaseException;
	boolean updatePassword(String login, String OldPassword, String NewPassword) throws InternalDatabaseException;
}
