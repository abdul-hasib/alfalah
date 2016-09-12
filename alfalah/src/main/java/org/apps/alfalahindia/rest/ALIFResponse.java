package org.apps.alfalahindia.rest;

public class ALIFResponse {

    String message;

    Object data;

    public String getMessage() {
        return message;
    }

    public String getData() {
        return JsonParser.toJson(data);
    }

    @Override
    public String toString() {
        return JsonParser.toJson(this);
    }
}
