package pl.tcs.oopproject.viewmodel.users;

import java.util.regex.Pattern;

public class Check {
	public static boolean correctEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailRegex);
		if (email == null) return false;
		return pattern.matcher(email).matches();
	}
	
	public static boolean correctTelephoneNumber(String number) {
		if (number == null) return true;
		if (number.length() == 9) number = "+48" + number;
		number = number.replaceAll("\\s", "");
		String numberRegex = "^\\+\\d{10,12}$";
		Pattern pattern = Pattern.compile(numberRegex);
		return pattern.matcher(number).matches();
		
	} //format : +48123456789, +48 123 456 789, the important stuff : international prefix if necessary
	
}
