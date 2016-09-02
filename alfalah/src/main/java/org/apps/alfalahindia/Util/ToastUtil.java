package org.apps.alfalahindia.Util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

    static Toast toast = null;

    public static void toast(Context ctx, Object message) {

        if(toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(ctx, message.toString(), Toast.LENGTH_SHORT);
        toast.show();
    }

}
