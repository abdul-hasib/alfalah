package org.apps.alfalahindia.rest;

import com.google.gson.JsonElement;

public class ALIFResponse {

    String message;

    JsonElement data;

    public String getMessage() {
        return message;
    }

    public JsonElement getData() {
        return data;
    }

    @Override
    public String toString() {
        return JsonParser.toJson(this);
    }
}
