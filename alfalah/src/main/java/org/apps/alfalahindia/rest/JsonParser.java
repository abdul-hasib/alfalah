package org.apps.alfalahindia.rest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abdulh on 9/1/2016.
 */
public class JsonParser {

    public static ErrorModel getError(String content) {

        ErrorModel errorModel = new ErrorModel();

        try {
            JSONObject jsonObject = new JSONObject(content);
            errorModel.setData(jsonObject.getString("data"));
            errorModel.setMessage(jsonObject.getString("message"));
            errorModel.setStatus(jsonObject.getString("status"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return errorModel;
    }


}
