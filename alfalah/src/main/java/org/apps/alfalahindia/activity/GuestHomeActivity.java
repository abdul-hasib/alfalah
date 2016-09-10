package org.apps.alfalahindia.activity;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.fragment.BaseFragment;
import org.apps.alfalahindia.fragment.MembersListFragment;
import org.apps.alfalahindia.fragment.ObjectivesFragment;

public class GuestHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager.addFragment(R.id.content_frame, new ObjectivesFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        BaseFragment fragment = null;

        switch (id) {
            case R.id.nav_gallery:
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_members_list:
                fragment = new MembersListFragment();
                break;
            case R.id.nav_objectives:
                fragment = new ObjectivesFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            fragmentManager.replaceFragment(R.id.content_frame, fragment);
            handleMenuItemSelection(item);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
