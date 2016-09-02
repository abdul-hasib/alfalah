package org.apps.alfalahindia.rest;

public class RestResponse {

    int returnCode;

    String message;

    String data;

    public String getData() {
        return data;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public String getMessage() {
        return message;
    }

}
