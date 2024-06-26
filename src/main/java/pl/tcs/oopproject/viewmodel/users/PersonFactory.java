package pl.tcs.oopproject.viewmodel.users;

import pl.tcs.oopproject.model.exception.*;
import pl.tcs.oopproject.model.users.Check;
import pl.tcs.oopproject.model.users.Person;
import pl.tcs.oopproject.postgresDatabaseIntegration.Users;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class PersonFactory {
    public static Person logIn(String login, String password) throws KoleoException {
        try {
            Users users=new Users();
            Person person = users.authenticate(login, password);
            ActiveUser.setActiveUser(login);
            ActiveUser.setPerson(person);
            return person;
        } catch (SQLException e) {
            throw new InvalidUsernameOrPasswordException();
        }

    } //log in

    public static Person create(String name, String surname, LocalDate dateOfBirth, String email, String phoneNumber) throws KoleoException {
        if (Objects.equals(phoneNumber, "")) phoneNumber = null;
        if (Check.incorrectTelephoneNumber(phoneNumber)) throw new InvalidTelephoneNumberException();
        if (Check.incorrectName(name) || Check.incorrectSurname(surname)) throw new InvalidNameOrSurnameException();
        if (Check.incorrectDateOfBirth(dateOfBirth)) throw new InvalidDateOfBirthException();
        if (Check.incorrectEmail(email)) throw new InvalidEmailException();
        return new Person(name, surname, dateOfBirth, email, phoneNumber);
    } //buy without signing up nor logging in


    public static Person create(String name, String surname, LocalDate dateOfBirth, String email, String phoneNumber, String login, String password) throws KoleoException {
        Users inserter = new Users();
        Person person = create(name, surname, dateOfBirth, email, phoneNumber);
        try {
            Users users=new Users();
            if (users.checkIfUserExists(login)) throw new ExistingUserException();
            if (!inserter.insert(person, login, password)) throw new SQLException();
            ActiveUser.setActiveUser(login);
            ActiveUser.setPerson(person);
            return person;
        } catch (SQLException e) {
            throw new InternalDatabaseException();
        }
    } //sign up with or without phoneNumber
}
