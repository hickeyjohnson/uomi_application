package ca.outercove.uomiapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import ca.outercove.uomiapplication.backendCommunication.RequestQueueSingleton;

public class LoginRegistrationActivity extends AppCompatActivity {

    // Fields of the activity
    private EditText mEmailAddress;
    private EditText mPassword;
    private Button mBtnSignInRegister;
    private LoginButton mLoginButton;
    private static final String EMAIL = "email";
    private static final String PROFILE = "public_profile";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private CallbackManager mCallbackManager;

    //LoginVerification object
    private LoginVerification loginVerification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);
        mCallbackManager = CallbackManager.Factory.create();

        // shared pref (needs to be called in or after onCreate
        this.pref = PreferenceManager.getDefaultSharedPreferences(this);

        // Set fields
        this.mEmailAddress = findViewById(R.id.etEmail);
        this.mPassword = findViewById(R.id.etPassword);
        this.mBtnSignInRegister = findViewById(R.id.btnLoginRegister);
        this.mLoginButton = findViewById(R.id.fb_login_button);

        mLoginButton.setReadPermissions(Arrays.asList(EMAIL, PROFILE));

        // Login Button Callback registration
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            // start the MainActivity when login successful
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
                    editor = pref.edit();
                    editor.putString("email", getEmail());
                    editor.putString("pw", getPassword());
                    editor.putString("firstName", "");
                    editor.putString("lastName", "");
                    editor.commit();
                    startMainActivity();
                }
            }
        });



        // If user is already logged in, skip this screen
        if (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired()) {
            System.out.println("Saved login from Facebook. Skipping sign-in screen.");
            startMainActivity();
        }
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
        if (AccessToken.getCurrentAccessToken() != null) {
            try {
                getFacebookUserInfo();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        // In here is the intent to start the main activity
//        signInOrRegisterUser();

    }

    private void getFacebookUserInfo() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        editor = pref.edit();
                        try {
                            editor.putString("email", object.getString("email"));
                            editor.putString("firstName", object.getString("first_name"));
                            editor.putString("lastName", object.getString("last_name"));
                            editor.commit();
                            signInOrRegisterUser();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,email,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void signInOrRegisterUser() {
        JSONObject userInfo = new JSONObject();
        String url = getString(R.string.base_url) + "/users";
        try {
            System.out.println(userInfo.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            userInfo.put("email", pref.getString("email", null));
            userInfo.put("pw", pref.getString("pw", null));
            userInfo.put("first_name", pref.getString("firstName", null));
            userInfo.put("last_name", pref.getString("lastName", null));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, userInfo, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                editor = pref.edit();
                try {
                    editor.putInt("userId", response.getInt("user_id"));
                    editor.commit();
                    Intent intent = new Intent(LoginRegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO
            }
        });

        RequestQueueSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);


    }

}
