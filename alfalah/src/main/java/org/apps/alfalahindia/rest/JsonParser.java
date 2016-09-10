package org.apps.alfalahindia.rest;

import com.google.gson.Gson;

/**
 * Created by abdulh on 9/1/2016.
 */
public class JsonParser {

    private static Gson gson = new Gson();

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object object) {
        return gson.toJson(object).toString();
    }

}
