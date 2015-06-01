package helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Abhinav on 6/1/2015.
 */
public class SessionManager {
    //Logcat TAG
    private static String TAG = SessionManager.class.getSimpleName();

    //Shared Prefs
    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    //Shared pref mode
    int PRIVATE_MODE = 0;

    //Shared preferences file name
    private static final String PREF_NAME = "EcomLogin";
    private static final String LOGGEDIN="isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref=_context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(LOGGEDIN,isLoggedIn);

        //commit changes
        editor.commit();

        Log.d(TAG,"User login session modified!");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(LOGGEDIN,false);
    }
}