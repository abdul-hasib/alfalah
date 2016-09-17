package org.apps.alfalahindia.Util;

import android.widget.Toast;

import org.apps.alfalahindia.activity.App;

public class ToastUtil {

    static Toast toast = null;

    public static void toast(Object message) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(App.getContext(), message.toString(), Toast.LENGTH_SHORT);
        toast.show();
    }

}
