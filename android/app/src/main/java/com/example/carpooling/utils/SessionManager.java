package com.example.carpooling.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "CarpoolingPrefs";
    
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_ROLE = "user_role";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void createLoginSession(int userId, String role) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USER_ROLE, role);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    
    public String getUserRole() {
        return prefs.getString(KEY_USER_ROLE, "");
    }
    
    public int getUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
