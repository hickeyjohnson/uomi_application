package ca.outercove.uomiapplication;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginRegistrationActivity extends AppCompatActivity {

    // Fields of the activity
    private EditText mEmailAddress;
    private EditText mPassword;
    private Button mBtnSignInRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);

        // Set fields
        this.mEmailAddress = findViewById(R.id.etEmail);
        this.mPassword = findViewById(R.id.etPassword);
        this.mBtnSignInRegister = findViewById(R.id.btnLoginRegister);

        // Set up button click listener
        mBtnSignInRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginValidation(mEmailAddress.getText().toString(), mPassword.getText().toString());
            }
        });

    }



    /******************************
        Scans a string to ensure it is a valid email
        address.
        @param email : email string from user input
        @return : true if valid email, false otherwise

     */
    private boolean validateEmail(String email) {


        // TODO: conditional email verification
        return true;
    }

    /**
     * Scans a string to ensure it is a valid password.
     * A valid password is defined as having at least 6
     * characters with at least one letter and number.
     *
     * @param pw : password string from user input
     * @return : true if valid password, false otherwise
     */
    private boolean validatePassword(String pw) {


        // TODO: conditional password verification
        return true;
    }

    /**
     *
     * @param em : String of user email
     * @param pw : String of user password
     */
    private void loginValidation(String em, String pw) {
        // TODO: search database for existing user, create new user if doesn't exist
        // Validate email and password
        if (validateEmail(em) && validatePassword(pw)) {
            Intent successfulLogin = new Intent(LoginRegistrationActivity.this, MainActivity.class);
            startActivity(successfulLogin);
        }
    }


}
