package org.apps.alfalahindia.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.apps.alfalahindia.Managers.ALIFFragmentManager;
import org.apps.alfalahindia.R;
import org.apps.alfalahindia.Util.PrefKeys;
import org.apps.alfalahindia.Util.Prefs;
import org.apps.alfalahindia.enums.UserRole;
import org.apps.alfalahindia.fragment.BaseFragment;
import org.apps.alfalahindia.fragment.MemberRegisterFragment;

public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    protected NavigationView navigationView;
    protected MenuItem activeMenuItem;
    ALIFFragmentManager fragmentManager;
    Prefs prefs;
    private String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = new Prefs(getApplicationContext());

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

        prepareHomePage(getUserRole());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    public void onClick(View view) {
        BaseFragment fragment = new MemberRegisterFragment();
        fragmentManager.pushFragment(R.id.content_frame, fragment);
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

    protected UserRole getUserRole() {
        Log.d(TAG, prefs.getString(PrefKeys.USER_USER_ROLE));
        return UserRole.valueOf(prefs.getString(PrefKeys.USER_USER_ROLE));
    }

    private void prepareHomePage(UserRole userRole) {

        navigationView.getMenu().setGroupVisible(R.id.group_member, false);
        navigationView.getMenu().setGroupVisible(R.id.group_admin, false);
        Log.d(TAG, userRole.toString());
        switch (userRole) {
            case SUPER_ADMIN:
                navigationView.getMenu().setGroupVisible(R.id.group_member, true);
                navigationView.getMenu().setGroupVisible(R.id.group_admin, true);
                break;
            case MEMBER:
                navigationView.getMenu().setGroupVisible(R.id.group_member, true);
                break;
            case GUEST:
                // do nothing
                break;
        }
    }
}
