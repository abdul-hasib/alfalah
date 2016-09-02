package org.apps.alfalahindia.background;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.apps.alfalahindia.Util.ProgressBarHandler;
import org.apps.alfalahindia.interfaces.OnTaskCompleted;
import org.apps.alfalahindia.rest.HttpManager;
import org.apps.alfalahindia.rest.RequestPackage;
import org.apps.alfalahindia.rest.RestResponse;

public class BackgroundTask extends AsyncTask<RequestPackage, String, RestResponse> {

    private ProgressBarHandler progressBarHandler;

    private OnTaskCompleted listener;

    public BackgroundTask(Activity activity, OnTaskCompleted listener) {
        progressBarHandler = new ProgressBarHandler(activity);
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        progressBarHandler.show();
    }

    @Override
    protected RestResponse doInBackground(RequestPackage... params) {
        String content = HttpManager.getData(params[0]);
        progressBarHandler.show();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Gson().fromJson(content, RestResponse.class);
    }

    @Override
    protected void onPostExecute(RestResponse result) {
        progressBarHandler.hide();
        listener.onTaskCompleted(result);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        // nothing
    }
}
