package ca.outercove.uomiapplication;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
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

        // Set up the app bar
        Toolbar appBar = findViewById(R.id.uomiAppBar);
        setSupportActionBar(appBar);

        // Retrieve NavController and setup the Navigation using nav_main
        mNavController = Navigation.findNavController(findViewById(R.id.nav_host_fragment));
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(navigation, mNavController);

        mNavController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                setActionBarTitle((String)destination.getLabel());
            }
        });
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String s) {
        //setActionBarTitle(s);
    }

    @Override
    public void onListFragmentInteraction(AccountsViewItem item) {
        mNavController.navigate(R.id.actionAccountSelect);
    }

}
