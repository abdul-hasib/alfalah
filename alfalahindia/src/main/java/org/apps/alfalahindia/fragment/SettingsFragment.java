package org.apps.alfalahindia.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import org.apps.alfalahindia.R;


public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}