package org.apps.alfalahindia.volley;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apps.alfalahindia.rest.ALIFRestResponse;
import org.apps.alfalahindia.rest.JsonParser;
import org.apps.alfalahindia.rest.RequestMethod;

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
            ALIFRestResponse alifRestResponse = JsonParser.fromJson(error, ALIFRestResponse.class);
            Log.d(TAG, alifRestResponse.getResponse().toString());
            volleyError = new VolleyError(alifRestResponse.getResponse().toString());
        }
        return volleyError;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {

        if (networkResponse != null && networkResponse.data != null) {
            String response = new String(networkResponse.data);
            try {
                response = URLDecoder.decode(response, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.d(TAG, response);
            ALIFRestResponse restResponse = JsonParser.fromJson(response, ALIFRestResponse.class);
            networkResponse = new NetworkResponse(restResponse.getResponse().toString().getBytes());
        }

        return super.parseNetworkResponse(networkResponse);
    }
}
