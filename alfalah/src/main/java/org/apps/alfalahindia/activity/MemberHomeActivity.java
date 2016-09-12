package org.apps.alfalahindia.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.Util.IntentKeys;
import org.apps.alfalahindia.Util.PrefKeys;
import org.apps.alfalahindia.fragment.DashboardFragment;
import org.apps.alfalahindia.fragment.MembersListFragment;
import org.apps.alfalahindia.fragment.ObjectivesFragment;

public class MemberHomeActivity extends BaseActivity {

    private String TAG = MemberHomeActivity.class.getSimpleName();
    private MenuItem activeMenuItem;

    private String memberDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFirstItemNavigationView(navigationView);
    }

    private void setFirstItemNavigationView(NavigationView navigationView) {
        navigationView.getMenu().getItem(0).setChecked(true);
        memberDetails = getIntent().getExtras().getString(IntentKeys.MEMBER_OBJECT);
        fragmentManager.addFragment(R.id.content_frame, DashboardFragment.newInstance(memberDetails));
        Log.d(TAG, prefs.getString(PrefKeys.USER_USER_ROLE));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;

        switch (id) {
            case R.id.nav_gallery:
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_dashboard:
                fragment = DashboardFragment.newInstance(memberDetails);
                break;
            case R.id.nav_members_list:
                fragment = MembersListFragment.newInstance(memberDetails);
                break;
            case R.id.nav_objectives:
                fragment = new ObjectivesFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            String backStateName = fragment.getClass().getName();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(backStateName).commit();

            if (activeMenuItem != null) {
                activeMenuItem.setChecked(false);
            }
            activeMenuItem = item;
            item.setChecked(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}