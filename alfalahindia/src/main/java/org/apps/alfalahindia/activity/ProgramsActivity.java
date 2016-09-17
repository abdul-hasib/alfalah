package org.apps.alfalahindia.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.Util.FileDownloader;
import org.apps.alfalahindia.Util.ToastUtil;
import org.apps.alfalahindia.res.Forms;


public class ProgramsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs);
    }

    public void downloadApplicationForm(View view) {

        String url = null;
        String name = null;
        String description = null;

        switch (view.getId()) {
            case R.id.almadad:
                url = Forms.AlMadad.URL;
                name = Forms.AlMadad.NAME;
                description = Forms.AlMadad.DESCRIPTION;
                break;
            case R.id.educare:
                url = Forms.Educare.URL;
                name = Forms.Educare.NAME;
                description = Forms.Educare.DESCRIPTION;
                break;
        }

        try {
            FileDownloader.download(url, name, description);
        } catch (Exception e) {
            ToastUtil.toast(e.getLocalizedMessage());
        }
    }


}
