package org.apps.alfalahindia.Util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    SharedPreferences preferences;

    Prefs prefs = null;
    Context context;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("ALIF_PREFS", Activity.MODE_PRIVATE);
    }

    public String getString(String key) {
        return preferences.getString(key, null);
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void setBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, false);
        preferences.edit().apply();
    }

    public void setInt(String key, int value) {
        preferences.edit().putInt(key, value);
        preferences.edit().apply();
    }

}