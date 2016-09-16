package org.apps.alfalahindia.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {

    private static final String BASE_URI = "http://www.alfalahindia.org/rest";

    private static String getURI(String uri) {
        return BASE_URI + uri;
    }

    public static String getData(RequestPackage requestPackage) {

        BufferedReader reader = null;
        String uri = requestPackage.getUri();

        if (requestPackage.getMethod() == RequestMethod.GET) {
            uri += "?" + requestPackage.getEncodedParams();
        }

        try {
            URL url = new URL(getURI(uri));

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod(requestPackage.getMethod().toString());

            if (requestPackage.getMethod() == RequestMethod.POST) {
                httpURLConnection.setDoOutput(false);
                OutputStreamWriter writer = new OutputStreamWriter(httpURLConnection.getOutputStream());
                writer.write(requestPackage.getEncodedParams());
                writer.flush();
            }

            int status = httpURLConnection.getResponseCode();
            if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                reader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }

    public enum RequestMethod {
        GET, POST
    }

}
