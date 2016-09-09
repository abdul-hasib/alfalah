package org.apps.alfalahindia.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefsEditor;

    Prefs prefs = null;
    Context context;

    public Prefs(Context context) {
        sharedPreferences = context.getSharedPreferences("USER_PREFS", Activity.MODE_PRIVATE);
        prefsEditor = sharedPreferences.edit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public void setString(String key, String value) {
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public void setBoolean(String key, boolean value) {
        prefsEditor.putBoolean(key, false);
        prefsEditor.commit();
    }

    public void setInt(String key, int value) {
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }

}