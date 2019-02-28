package ca.outercove.uomiapplication;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import ca.outercove.uomiapplication.fragments.AccountsViewFragment;
import ca.outercove.uomiapplication.fragments.DashboardFragment;
import ca.outercove.uomiapplication.fragments.NotificationsFragment;
import ca.outercove.uomiapplication.appObjects.AccountsViewContent.AccountsViewItem;
import ca.outercove.uomiapplication.fragments.SingleAccountFragment;

public class MainActivity extends AppCompatActivity implements
AccountsViewFragment.OnListFragmentInteractionListener,
NotificationsFragment.OnFragmentInteractionListener,
DashboardFragment.OnFragmentInteractionListener,
SingleAccountFragment.OnFragmentInteractionListener {

    protected NavController mNavController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        // Retrieve NavController and setup the Navigation using nav_main
        mNavController = Navigation.findNavController(findViewById(R.id.nav_host_fragment));
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(navigation, mNavController);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(AccountsViewItem item) {
        mNavController.navigate(R.id.navigation_single_account);
    }

}
