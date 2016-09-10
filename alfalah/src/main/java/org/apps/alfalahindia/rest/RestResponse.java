package org.apps.alfalahindia.rest;

import com.google.gson.Gson;

public class RestResponse {

    String message;

    Member member;

    public String getMessage() {
        return message;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this).toString();
    }
}
