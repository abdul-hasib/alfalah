package org.apps.alfalahindia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;

import org.apps.alfalahindia.Util.FileDownloader;
import org.apps.alfalahindia.Util.Msg;
import org.apps.alfalahindia.adapters.ObjectivesAdapter;
import org.apps.alfalahindia.res.Forms;

import java.util.Timer;
import java.util.TimerTask;


public class ObjectivesActivity extends Activity {

    int currentPage = 1;
    int numberOfPages = 1;
    ViewPager features;
    Timer swipeTimer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectives);

        ObjectivesAdapter mCustomPagerAdapter = new ObjectivesAdapter(this);

        numberOfPages = mCustomPagerAdapter.getCount();

        features = (ViewPager) findViewById(R.id.pager);
        features.setAdapter(mCustomPagerAdapter);

        final Handler handler = new Handler();

        final Runnable pageSwitcher = new Runnable() {
            public void run() {
                if (currentPage == numberOfPages - 1) {
                    currentPage = 0;
                }
                features.setCurrentItem(currentPage++, true);
            }
        };

        swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(pageSwitcher);
            }
        }, 500, 3000);

    }


    public void downloadApplications(View view) {
        Intent intent = new Intent(getApplicationContext(), ProgramsActivity.class);
        startActivity(intent);
    }

}