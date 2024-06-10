package pl.tcs.oopproject.model.users;


import pl.tcs.oopproject.model.exception.KoleoException;

import java.time.LocalDate;
import java.util.Objects;

public class Person {
    public static Person DEFAULT = new Person("DEFAULT", "Garmadon", LocalDate.of(2001, 9, 11), "ninjago@gmail.com", null);
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
        return Objects.toString(name, "");
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return Objects.toString(surname, "");
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name and Surname: ").append(name).append(" ").append(surname).append("\n");
        sb.append("Date of Birth: ").append(dateOfBirth).append("\n");
        sb.append("Email Address: ").append(emailAddress).append("\n");
        if (telephoneNumber != null) {
            sb.append("Phone Number: ").append(telephoneNumber).append("\n");
        }
        return sb.toString();
    }
}
