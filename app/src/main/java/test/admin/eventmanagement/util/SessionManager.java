package test.admin.eventmanagement.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import test.admin.eventmanagement.DBHelper;


public class SessionManager {
    public static final String PREF_NAME = "session_user";
    public static final String IS_LOGIN = "IsLoggedIn";
    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String MOBILE = "mobile";
    public static final String PASSWORD = "password";
    public static final String USER_TYPE = "user_type";

    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createSession(String userId,String userName,String usertoken) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(USER_ID, userId);
        editor.putString(USER_NAME, userName);

        editor.commit();
    }

    public void setIsLogin(){
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

    public boolean isLogin(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setUserType(String userType){
        editor.putString(USER_TYPE, userType);
        editor.commit();
    }

    public String getUserType(){
        return  pref.getString(USER_TYPE, DBHelper.STUDENT);
    }

    public void clearSession() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
    }

    /**
     * Quick check for login
     * *
     */
//    public void setGeoDone(boolean done){
//        editor.putBoolean(GEO_DONE,done);
//        editor.commit();
//    }
//        public boolean isGeoDone(){
//                return pref.getBoolean(GEO_DONE,false);
//        }
}
