package org.apps.alfalahindia.Util;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by abdulh on 9/6/2015.
 */
public class Msg {

    private static Toast toast = null;

    public static void showLong(Context context, String msg) {

        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);

        if (v != null) {
            v.setGravity(Gravity.CENTER);
        }

        toast.show();
    }

}
