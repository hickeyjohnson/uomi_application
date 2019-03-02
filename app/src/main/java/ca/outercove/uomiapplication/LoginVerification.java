package ca.outercove.uomiapplication;

import android.content.Intent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginVerification extends LoginRegistrationActivity {

    //Email String
    private String email;

    //Password String
    private String password;

    // Email Regex java
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

    //Password Regex
    // FIXME: changing the + to {6, 20} eliminates the need to check length separately
    private static final String PASSWORD_REGEX = "[A-Za-z0-9]+";

    // static Pattern object, since pattern is fixed
    private static Pattern pattern;

    // non-static Matcher object because it's created from the input String
    private Matcher matcher;



    /**
     * Constructor for LoginVerification class
     * @param login - LoginRegistrationActivity object
     */
    public LoginVerification(LoginRegistrationActivity login) {
        email = login.getEmail();
        password = login.getPassword();
    }

    /******************************
     Scans a string to ensure it is a valid email
     address.
     @param email : email string from user input
     @return : true if valid email, false otherwise

     */
    protected boolean validateEmail(String email) {
        pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Scans a string to ensure it is a valid password.
     * A valid password is defined as having at least 6
     * characters with at least one letter and number.
     *
     * @param password : password string from user input
     * @return : true if valid password, false otherwise
     */
    protected boolean validatePassword(String password) {
        if (password.length() > 20 || password.length() < 6 ) {
            return false;
        }
        pattern = Pattern.compile(PASSWORD_REGEX, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /**
     *
     * @param em : String of user email
     * @param pw : String of user password
     */
    protected boolean loginValidation(String em, String pw) {
        // TODO: search database for existing user, create new user if doesn't exist
        // Validate email and password
        return validateEmail(em) && validatePassword(pw);

    }
}
