package live.Abhinav.ecommerce.app;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import helper.SQLiteHandler;
import helper.SessionManager;

import java.util.HashMap;


public class DashboardActivity extends ActionBarActivity {
    //Logcat TAG
    private static final String TAG=DashboardActivity.class.getSimpleName();

    private TextView txtName,txtEmail;
    private Button btnLogout;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        txtName= (TextView) findViewById(R.id.name);
        txtEmail= (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        //Sqlite database handler
        db=new SQLiteHandler(getApplicationContext());

        //SessionManager
        session = new SessionManager(getApplicationContext());

        if(!session.isLoggedIn()) {
            logoutUser();
        }

        //fetch user details from SQLITE DB
        HashMap<String,String> user  =db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        Log.d(TAG,user.toString());
        //Display the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);

        //Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    /**
     * Function to logout the user. Will set isLoggedIn flag to fasle in sharedPreferences file.
     * Clears the user data from sqlite  users table.
     */
    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();

        //Launching the login activity
        Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
