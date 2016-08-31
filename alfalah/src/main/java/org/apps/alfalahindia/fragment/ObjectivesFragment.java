package org.apps.alfalahindia.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.adapters.ObjectivesAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class ObjectivesFragment extends Fragment {

    int currentPage = 1;
    int numberOfPages = 1;
    ViewPager features;
    Timer swipeTimer = new Timer();

    public ObjectivesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_objectives, container, false);

        ObjectivesAdapter mCustomPagerAdapter = new ObjectivesAdapter(getActivity());

        numberOfPages = mCustomPagerAdapter.getCount();

        features = (ViewPager) view.findViewById(R.id.pager);
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
        
        return view;
    }

}
