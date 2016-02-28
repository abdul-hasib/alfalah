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


public class MainActivity extends AppCompatActivity {

    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;
    ImageView membershipFileImage;
    ImageView registrationFileImage;
    // Progress Dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Image view to show image after downloading
        membershipFileImage = (ImageView) findViewById(R.id.membershipFileImage);
        registrationFileImage = (ImageView) findViewById(R.id.registrationFileImage);
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

    public void downloadRegistrationForm(View view) {
        try {
            FileDownloader.download(getApplicationContext(), Forms.Registration.URL,
                    Forms.Registration.NAME, Forms.Registration.DESCRIPTION);
        } catch (Exception e) {
            Msg.showLong(getApplicationContext(), e.getLocalizedMessage());
        }
    }


    public void downloadMembershipForm(View view) {
        try {
            FileDownloader.download(getApplicationContext(), Forms.Membership.URL,
                    Forms.Membership.NAME, Forms.Membership.DESCRIPTION);
        } catch (Exception e) {
            Msg.showLong(getApplicationContext(), e.getLocalizedMessage());
        }
    }


}
