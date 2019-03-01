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

    //LoginVerification object
    private LoginVerification loginVerification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);

        // Set fields
        this.mEmailAddress = findViewById(R.id.etEmail);
        this.mPassword = findViewById(R.id.etPassword);
        this.mBtnSignInRegister = findViewById(R.id.btnLoginRegister);

        loginVerification = new LoginVerification(this);

        // Set up button click listener
        mBtnSignInRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!loginVerification.validateEmail(getEmail())){
                    mEmailAddress.setError("Invalid e-mail");
                }
                if (!loginVerification.validatePassword(getPassword())) {
                    mPassword.setError("Must be alphanumeric with 6-20 characters");
                }
                if (loginVerification.loginValidation(getEmail(), getPassword())){
                    Intent successfulLogin = new Intent(LoginRegistrationActivity.this, MainActivity.class);
                    // TODO: store email in the app and verify with database
                    startActivity(successfulLogin);
                    // Finish activity so users don't go back to login screen when back button pressed
                    finish();
                }
            }
        });

    }

    /**
     *
     * @return user inputted text in email field to a string
     */
    protected String getEmail() {
        return mEmailAddress.getText().toString();
    }

    /**
     *
     * @return user inputted text in password field to a string
     */
    protected String getPassword() {
        return mPassword.getText().toString();
    }
}
