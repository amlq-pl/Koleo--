package pl.tcs.oopproject.model.users;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class Check {
    static int minNameLength = 2;
    static int maxNameLength = 20;
    static int minSurnameLength = 2;
    static int maxSurnameLength = 20;
    static int minAge = 12;
    static int maxAge = 170;

    public static boolean incorrectEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) return true;
        return !pattern.matcher(email).matches();
    }

    public static boolean incorrectTelephoneNumber(String number) {
        if (number == null) return false;
        number = number.replaceAll("\\s", "");
        if (number.length() == 9) number = "+48" + number;
        String numberRegex = "^\\+\\d{10,12}$";
        Pattern pattern = Pattern.compile(numberRegex);
        return !pattern.matcher(number).matches();

    } //format : +48123456789, +48 123 456 789, the important stuff : international prefix if necessary

    public static boolean incorrectName(String name) {
        return name.length() > maxNameLength || name.length() < minNameLength;
    }

    public static boolean incorrectSurname(String name) {
        return name.length() > maxSurnameLength || name.length() < minSurnameLength;
    }

    public static boolean incorrectDateOfBirth(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears() < minAge || Period.between(dateOfBirth, LocalDate.now()).getYears() > maxAge;
    }


}
