package ca.outercove.uomiapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;

public class AccountsActivity extends MainActivity {

    private FloatingActionButton mBtnAddAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        Toolbar toolbar = findViewById(R.id.accounts_toolbar);
        setSupportActionBar(toolbar);  // Sets the toolbar as the app bar

        this.mBtnAddAccount = findViewById(R.id.fab);
        mBtnAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu
        getMenuInflater().inflate(R.menu.menu_accounts_action_bar, menu);
        return true;
    }

}
