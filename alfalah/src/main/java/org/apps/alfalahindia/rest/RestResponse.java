package org.apps.alfalahindia.rest;

import com.google.gson.Gson;

public class RestResponse {

    String response;

    public String getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this).toString();
    }
}
