package ca.outercove.uomiapplication;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements
AccountsFragment.OnFragmentInteractionListener,
SettingsFragment.OnFragmentInteractionListener,
NotificationsFragment.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);
        NavController navController = Navigation.findNavController(findViewById(R.id.nav_host_fragment));

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(navigation, navController);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
