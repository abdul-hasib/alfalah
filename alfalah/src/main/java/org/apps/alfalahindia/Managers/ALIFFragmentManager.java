package org.apps.alfalahindia.Managers;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import org.apps.alfalahindia.fragment.BaseFragment;

public class ALIFFragmentManager {

    FragmentManager fm;

    public ALIFFragmentManager(AppCompatActivity activity) {
        fm = activity.getSupportFragmentManager();
    }

    private void handleFragment(@IdRes int containerViewId,
                                @NonNull BaseFragment fragment, boolean popLastFragment) {

        if (popLastFragment) {
            fm.popBackStackImmediate();
        }

        fm.beginTransaction().replace(containerViewId, fragment, fragment.getFragmentTag())
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    public void replaceFragment(@IdRes int containerViewId,
                                @NonNull BaseFragment fragment) {
        handleFragment(containerViewId, fragment, true);
    }

    public void pushFragment(@IdRes int containerViewId,
                             @NonNull BaseFragment fragment) {
        handleFragment(containerViewId, fragment, false);
    }

    public void addFragment(@IdRes int containerViewId,
                            @NonNull BaseFragment fragment) {
        fm.beginTransaction()
                .add(containerViewId, fragment, fragment.getFragmentTag())
                .disallowAddToBackStack()
                .commit();
    }

}
