package ca.outercove.uomiapplication.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import com.facebook.login.LoginManager;
import ca.outercove.uomiapplication.LoginRegistrationActivity;
import ca.outercove.uomiapplication.R;

public class PreferencesFragment extends PreferenceFragmentCompat  {

    private Preference logoutPref;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.settings_ui);
        logoutPref = findPreference("logout_preference");
        logoutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (preference == logoutPref) {
                    LoginManager facebookManager = LoginManager.getInstance();
                    facebookManager.logOut();
                    Intent intent = new Intent(getActivity(), LoginRegistrationActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });


    }


}