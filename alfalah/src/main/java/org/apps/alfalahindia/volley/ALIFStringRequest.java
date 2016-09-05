package org.apps.alfalahindia.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apps.alfalahindia.rest.RequestMethod;
import org.json.JSONException;
import org.json.JSONObject;

public class ALIFStringRequest extends StringRequest {

    public ALIFStringRequest(RequestMethod method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method.getValue(), url, listener, errorListener);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            volleyError = new VolleyError(new String(volleyError.networkResponse.data));
        }
        return volleyError;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse networkResponse) {

        if (networkResponse != null && networkResponse.data != null) {
            String data = null;
            try {
                JSONObject jsonObject = new JSONObject(new String(networkResponse.data));
                data = jsonObject.get("response").toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            networkResponse = new NetworkResponse(data.getBytes());
        }

        return super.parseNetworkResponse(networkResponse);
    }
}
