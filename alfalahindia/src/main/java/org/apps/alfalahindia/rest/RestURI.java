package org.apps.alfalahindia.rest;


import org.apps.alfalahindia.Util.PrefKeys;
import org.apps.alfalahindia.Util.Prefs;
import org.apps.alfalahindia.Util.ToastUtil;

import java.util.Map;

public class RestURI {

    private static final String SERVER_BASE_URI = "http://alfalahindia.org/rest/v1/api";
    private static final String LOCAL_BASE_URI = "http://?/alifrest/v1/api";

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

        if (Prefs.getBoolean(PrefKeys.OFFLINE_MODE)) {
            String server = Prefs.getString(PrefKeys.OFFLINE_SERVER);

            if (server == null) {
                ToastUtil.toast("LOCAL SERVER CAN NOT BE NULL");
                return SERVER_BASE_URI;
            }

            return LOCAL_BASE_URI.replace("?", server);
        }

        return SERVER_BASE_URI;
    }

    public static String getUri(String path) {
        return getBaseURI() + path;
    }
}
