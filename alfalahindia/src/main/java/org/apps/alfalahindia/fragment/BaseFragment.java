package org.apps.alfalahindia.fragment;


import android.app.Fragment;
import android.os.Bundle;

import org.apps.alfalahindia.Util.Prefs;


public class BaseFragment extends Fragment {

    Prefs prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new Prefs(getActivity().getApplicationContext());
    }

    public String getFragmentTag() {
        return this.getClass().getSimpleName();
    }

    protected void finish() {
        getActivity().getFragmentManager().popBackStack();
    }

}
