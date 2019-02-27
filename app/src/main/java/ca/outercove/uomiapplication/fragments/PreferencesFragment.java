package ca.outercove.uomiapplication.fragments;


import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.outercove.uomiapplication.R;

/**
 * A simple {@link PreferenceFragment} subclass.
 */
public class PreferencesFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
//        super.onCreate(bundle);

        addPreferencesFromResource(R.xml.settings_ui);
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        addPreferencesFromResource(R.xml.settings_ui);
//    }

}
