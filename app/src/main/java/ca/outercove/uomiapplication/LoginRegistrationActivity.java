package ca.outercove.uomiapplication;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginRegistrationActivity extends AppCompatActivity {

    // Fields of the activity
    private EditText mEmailAddress;
    private EditText mPassword;
    private Button mBtnSignInRegister;
    private LoginButton mLoginButton;
    private static final String EMAIL = "email";

    private CallbackManager mCallbackManager;

    //LoginVerification object
    private LoginVerification loginVerification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);
        mCallbackManager = CallbackManager.Factory.create();

        // Set fields
        this.mEmailAddress = findViewById(R.id.etEmail);
        this.mPassword = findViewById(R.id.etPassword);
        this.mBtnSignInRegister = findViewById(R.id.btnLoginRegister);
        this.mLoginButton = findViewById(R.id.fb_login_button);

        mLoginButton.setReadPermissions(Arrays.asList(EMAIL));

        // If user is already logged in, skip this screen
        if (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired()) {
            System.out.println("Saved login from Facebook. Skipping sign-in screen.");
            startMainActivity();
        }

        // Callback registration
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startMainActivity();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

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
//                    Intent successfulLogin = new Intent(LoginRegistrationActivity.this, MainActivity.class);
                    // TODO: store email in the app and verify with database
//                    startActivity(successfulLogin);
                    // Finish activity so users don't go back to login screen when back button pressed
//                    finish();
                    startMainActivity();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
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

    private void startMainActivity() {
        Intent intent = new Intent(LoginRegistrationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
