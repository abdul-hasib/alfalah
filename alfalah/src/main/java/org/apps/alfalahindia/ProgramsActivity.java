package org.apps.alfalahindia;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.apps.alfalahindia.Util.FileDownloader;
import org.apps.alfalahindia.Util.Msg;
import org.apps.alfalahindia.res.Forms;


public class ProgramsActivity extends AppCompatActivity {

    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;
    ImageView membershipFileImage;
    ImageView registrationFileImage;
    // Progress Dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            FileDownloader.download(getApplicationContext(), url,
                    name, description);
        } catch (Exception e) {
            Msg.showLong(getApplicationContext(), e.getLocalizedMessage());
        }
    }


}