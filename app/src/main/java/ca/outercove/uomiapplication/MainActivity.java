package ca.outercove.uomiapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import ca.outercove.uomiapplication.appObjects.NotificationsContent.NotificationsListItem;
import ca.outercove.uomiapplication.appObjects.SingleAccountViewContent;
import ca.outercove.uomiapplication.fragments.AccountsViewFragment;
import ca.outercove.uomiapplication.fragments.DashboardFragment;
import ca.outercove.uomiapplication.appObjects.AccountsViewContent.AccountsViewItem;
import ca.outercove.uomiapplication.fragments.SingleAccountFragment;
import ca.outercove.uomiapplication.fragments.NotificationsFragment;

public class MainActivity extends AppCompatActivity implements
AccountsViewFragment.OnListFragmentInteractionListener,
NotificationsFragment.OnListFragmentInteractionListener,
DashboardFragment.OnFragmentInteractionListener,
SingleAccountFragment.OnListFragmentInteractionListener {

    protected NavController mNavController;

    private SharedPreferences pref;
    private Integer userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        this.pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        System.out.println(this.pref.getInt("userId", -1));
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
    public void onListFragmentInteraction(SingleAccountViewContent.TransactionItem item) {

    }

    @Override
    public void onFragmentInteraction(String s) {
        //setActionBarTitle(s);
    }

    @Override
    public void onListFragmentInteraction(AccountsViewItem item) {
        mNavController.navigate(R.id.actionAccountSelect);
    }

    @Override
    public void onListFragmentInteraction(NotificationsListItem item) {

    }

}
