package org.apps.alfalahindia.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.apps.alfalahindia.Managers.ALIFFragmentManager;
import org.apps.alfalahindia.R;
import org.apps.alfalahindia.Util.PrefKeys;
import org.apps.alfalahindia.Util.Prefs;
import org.apps.alfalahindia.enums.UserRole;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected NavigationView navigationView;
    protected MenuItem activeMenuItem;
    ALIFFragmentManager fragmentManager;
    private String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = new ALIFFragmentManager(this);
    }

    protected void handleMenuItemSelection(MenuItem item) {

        if (activeMenuItem != null) {
            activeMenuItem.setChecked(false);
        }
        activeMenuItem = item;
        activeMenuItem.setChecked(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handleMenuItemSelection(navigationView.getMenu().getItem(0));
    }

    protected void prepareHomePage(UserRole userRole) {

        // update the role in preferences
        Prefs.setString(PrefKeys.MEMBER_ROLE, userRole.toString());

        navigationView.getMenu().setGroupVisible(R.id.group_member, false);
        navigationView.getMenu().setGroupVisible(R.id.group_admin, false);

        switch (userRole) {
            case SUPER_ADMIN:
            case ADMIN:
                navigationView.getMenu().setGroupVisible(R.id.group_member, true);
                navigationView.getMenu().setGroupVisible(R.id.group_admin, true);
                break;
            case MEMBER:
                navigationView.getMenu().setGroupVisible(R.id.group_member, true);
                break;
        }
    }
}
