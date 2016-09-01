package org.apps.alfalahindia.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.rest.ErrorModel;
import org.apps.alfalahindia.rest.HttpManager;
import org.apps.alfalahindia.rest.JsonParser;
import org.apps.alfalahindia.rest.RequestPackage;

import java.util.ArrayList;
import java.util.List;

public class BackgroundTask extends AsyncTask<RequestPackage, String, String> {

    ProgressDialog progressDialog;
    Activity activity;
    Context context;

    List<BackgroundTask> tasks;
    ErrorModel errorModel;

    public BackgroundTask(Activity activity) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        tasks = new ArrayList<>();
        progressDialog = ProgressDialog.show(activity, context.getResources().getString(R.string.app_name), "Please wait");
    }

    @Override
    protected void onPreExecute() {
        if (tasks.size() == 0) {
            progressDialog.hide();
        }
        tasks.add(this);
    }

    @Override
    protected String doInBackground(RequestPackage... params) {
        String content = HttpManager.getData(params[0]);
        return content;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("TAG", result);

        errorModel = JsonParser.getError(result);

        Log.d("TAG", errorModel.getData());

        tasks.remove(this);
        if (tasks.size() == 0) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Log.d("TAG", values[0]);
    }
}
