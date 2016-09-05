package org.apps.alfalahindia.background;

import android.app.Activity;
import android.os.AsyncTask;

import com.android.volley.Response;

import org.apps.alfalahindia.Util.ProgressBarHandler;
import org.apps.alfalahindia.interfaces.OnTaskCompleted;
import org.apps.alfalahindia.rest.HttpManager;
import org.apps.alfalahindia.rest.RequestPackage;

public class BackgroundTask extends AsyncTask<RequestPackage, String, String> {

    private ProgressBarHandler progressBarHandler;

    private OnTaskCompleted listener;

    public BackgroundTask(Activity activity, OnTaskCompleted listener, Response.ErrorListener errorListener) {
        progressBarHandler = new ProgressBarHandler(activity);
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        progressBarHandler.show();
    }

    @Override
    protected String doInBackground(RequestPackage... params) {
        String content = HttpManager.getData(params[0]);
        progressBarHandler.show();
        return content;
    }

    @Override
    protected void onPostExecute(String result) {
        progressBarHandler.hide();
        listener.onTaskCompleted(result);
    }

    @Override
    protected void onProgressUpdate(String... values) {
        // nothing
    }
}
