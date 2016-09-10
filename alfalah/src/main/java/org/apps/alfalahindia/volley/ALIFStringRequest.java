package org.apps.alfalahindia.volley;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apps.alfalahindia.rest.JsonParser;
import org.apps.alfalahindia.rest.RequestMethod;
import org.apps.alfalahindia.rest.RestData;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ALIFStringRequest extends StringRequest {

    final String TAG = ALIFStringRequest.class.getSimpleName();

    public ALIFStringRequest(RequestMethod method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method.getValue(), url, listener, errorListener);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            String error = new String(volleyError.networkResponse.data);
            Log.d(TAG, error);
            try {
                error = URLDecoder.decode(error, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            RestData restData = JsonParser.fromJson(error, RestData.class);
            Log.d(TAG, restData.getResponse().toString());
            volleyError = new VolleyError(restData.getResponse().toString());
        }
        return volleyError;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {

        if (networkResponse != null && networkResponse.data != null) {
            String data = new String(networkResponse.data);
            Log.d(TAG, data);
            try {
                data = URLDecoder.decode(data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            RestData restData = JsonParser.fromJson(data, RestData.class);
            Log.d(TAG, restData.getResponse().toString());
            networkResponse = new NetworkResponse(restData.getResponse().toString().getBytes());
        }

        return super.parseNetworkResponse(networkResponse);
    }

}
