package org.apps.alfalahindia.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;

import org.apps.alfalahindia.rest.RequestMethod;

public class ALIFJsonRequest extends JsonRequest {

    public ALIFJsonRequest(RequestMethod method, String url, String requestBody, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method.getValue(), url, requestBody, listener, errorListener);
    }

    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            volleyError = new VolleyError(new String(volleyError.networkResponse.data));
        }
        return volleyError;
    }

    @Override
    public int compareTo(Object object) {
        return 0;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse networkResponse) {
        VolleyError volleyError = new VolleyError(networkResponse);
        volleyError = parseNetworkError(volleyError);
        return Response.error(volleyError);
    }
}
