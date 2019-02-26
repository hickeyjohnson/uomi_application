package ca.outercove.uomiapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements
AccountsFragment.OnFragmentInteractionListener,
SettingsFragment.OnFragmentInteractionListener,
NotificationsFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_accounts:
                    FragmentTransaction accountsTransaction = getSupportFragmentManager().beginTransaction();
                    selectedFragment = AccountsFragment.newInstance("foo", "bar");
                    accountsTransaction.replace(R.id.content, selectedFragment);
                    accountsTransaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    FragmentTransaction settingsTransaction = getSupportFragmentManager().beginTransaction();
                    selectedFragment = SettingsFragment.newInstance("foo", "bar");
                    settingsTransaction.replace(R.id.content, selectedFragment);
                    settingsTransaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    FragmentTransaction notificationsTransaction = getSupportFragmentManager().beginTransaction();
                    selectedFragment = NotificationsFragment.newInstance("foo", "bar");
                    notificationsTransaction.replace(R.id.content, selectedFragment);
                    notificationsTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, AccountsFragment.newInstance("foo", "bar"));
        transaction.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
