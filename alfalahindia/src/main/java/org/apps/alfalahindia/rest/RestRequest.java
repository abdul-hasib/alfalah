package org.apps.alfalahindia.rest;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class RestRequest extends com.android.volley.Request<ALIFResponse> {

    private static final String BASE_URI = "http://www.alfalahindia.org/rest";

    private Response.Listener<ALIFResponse> listener;
    private Map<String, String> params;

    public RestRequest(String url, Map<String, String> params,
                       Response.Listener<ALIFResponse> responseListener, Response.ErrorListener errorListener) {
        super(Method.POST, BASE_URI + url, errorListener);
        this.listener = responseListener;
        this.params = params;
    }

    public RestRequest(int method, String url, Map<String, String> params,
                       Response.Listener<ALIFResponse> responseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = responseListener;
        this.params = params;
    }

    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return params;
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {

        Log.d("ALIF", volleyError.toString());

        return super.parseNetworkError(volleyError);
    }

    @Override
    protected Response<ALIFResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new Gson().fromJson(jsonString, ALIFResponse.class),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(ALIFResponse response) {
        listener.onResponse(response);
    }

}