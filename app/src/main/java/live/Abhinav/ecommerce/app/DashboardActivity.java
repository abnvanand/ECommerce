package live.Abhinav.ecommerce.app;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import helper.SQLiteHandler;
import helper.SessionManager;

import java.util.HashMap;


public class DashboardActivity extends ActionBarActivity {
    //Logcat TAG
    private static final String TAG = DashboardActivity.class.getSimpleName();

    //Toolbar
    private Toolbar toolbar;

    private TextView txtName, txtEmail;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_nav_below);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);

        //Sqlite database handler
        db = new SQLiteHandler(getApplicationContext());

        //SessionManager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        //fetch user details from SQLITE DB
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        Log.d(TAG, user.toString());
        //Display the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);


    }

    /**
     * Function to logout the user. Will set isLoggedIn flag to fasle in sharedPreferences file.
     * Clears the user data from sqlite  users table.
     */
    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();

        //Launching the login activity
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            startActivity(new Intent(this, SubActivity.class));
            return true;
        }

        //Logout button click
        if (id == R.id.action_logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show();
            logoutUser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}