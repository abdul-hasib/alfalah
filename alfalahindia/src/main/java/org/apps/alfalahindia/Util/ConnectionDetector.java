package org.apps.alfalahindia.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apps.alfalahindia.activity.App;

public class ConnectionDetector {

    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        }

        ToastUtil.toast("There is not internet connection");

        return false;
    }
}