package org.apps.alfalahindia.rest;

/**
 * Created by abdulh on 9/5/2016.
 */
public enum RequestMethod {

    GET(0), POST(1), PUT(2), DELETE(3);

    int value;

    RequestMethod(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
