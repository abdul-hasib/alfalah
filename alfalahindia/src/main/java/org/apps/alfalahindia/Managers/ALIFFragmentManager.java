package org.apps.alfalahindia.Managers;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

public class ALIFFragmentManager {

    FragmentManager fm;

    public ALIFFragmentManager(Activity activity) {
        fm = activity.getFragmentManager();
    }

    private void handleFragment(@IdRes int containerViewId,
                                @NonNull Fragment fragment, boolean popLastFragment) {

        if (popLastFragment) {
            fm.popBackStackImmediate();
        }

        fm.beginTransaction().replace(containerViewId, fragment, fragment.getTag())
                .addToBackStack(fragment.getClass().getName())
                .commit();
    }

    public void replaceFragment(@IdRes int containerViewId,
                                @NonNull Fragment fragment) {
        handleFragment(containerViewId, fragment, true);
    }

    public void pushFragment(@IdRes int containerViewId,
                             @NonNull Fragment fragment) {
        handleFragment(containerViewId, fragment, false);
    }

    public void addFragment(@IdRes int containerViewId,
                            @NonNull Fragment fragment) {
        fm.beginTransaction()
                .add(containerViewId, fragment, fragment.getTag())
                .disallowAddToBackStack()
                .commit();
    }

}
