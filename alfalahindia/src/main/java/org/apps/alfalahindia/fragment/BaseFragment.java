package org.apps.alfalahindia.fragment;


import android.app.Fragment;
import android.os.Bundle;


public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String getFragmentTag() {
        return this.getClass().getSimpleName();
    }

    protected void finish() {
        getActivity().getFragmentManager().popBackStack();
    }

}
