package org.apps.alfalahindia.rest;


import android.util.Log;

import org.apps.alfalahindia.Util.PrefKeys;
import org.apps.alfalahindia.Util.Prefs;

import java.util.Map;

public class RestURI {

    private static final String SERVER_BASE_URI = "http://alfalahindia.org/rest/v1/api";
    private static final String LOCAL_BASE_URI = "http://192.168.184.1/alifrest/v1/api";

    private static String TAG = RestURI.class.getSimpleName();

    public static String getUri(String path, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(getBaseURI());
        sb.append(path);
        int i = 1;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key;
            String value;
            key = entry.getKey();
            value = entry.getValue();
            if (i == 1) {
                sb.append("?").append(key).append("=").append(value);
            } else {
                sb.append("&").append(key).append("=").append(value);
            }

            i++;

        }
        String url = sb.toString();

        System.out.println(url);

        return url;
    }

    private static String getBaseURI() {

        boolean isOnline = Prefs.getBoolean("pref_work_online");
        Log.d(TAG, "isOnline: " + isOnline);

        String authToken = Prefs.getString(PrefKeys.USER_AUTH_TOKEN);
        Log.d(TAG, "authToken: " + authToken);

        return SERVER_BASE_URI;
    }

    public static String getUri(String path) {
        return getBaseURI() + path;
    }
}
