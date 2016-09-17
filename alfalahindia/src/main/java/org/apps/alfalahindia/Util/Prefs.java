package org.apps.alfalahindia.Util;

import android.app.Activity;
import android.content.SharedPreferences;

import org.apps.alfalahindia.activity.App;

public class Prefs {

    static SharedPreferences sharedPref = null;

    private static SharedPreferences getSharedPref() {
        if (sharedPref == null) {
            sharedPref = App.getContext().getSharedPreferences("ALIF_PREFS", Activity.MODE_PRIVATE);
        }
        return sharedPref;
    }

    private static SharedPreferences.Editor getEditor() {
        return getSharedPref().edit();
    }

    public static String getString(String key) {
        return getSharedPref().getString(key, null);
    }

    public static boolean getBoolean(String key) {
        return getSharedPref().getBoolean(key, false);
    }

    public static int getInt(String key) {
        return getSharedPref().getInt(key, 0);
    }

    public static void setString(String key, String value) {
        getEditor().putString(key, value).apply();
    }

    public static void setBoolean(String key, boolean value) {
        getEditor().putBoolean(key, false).apply();
    }

    public static void setInt(String key, int value) {
        getEditor().putInt(key, value).apply();
    }

}