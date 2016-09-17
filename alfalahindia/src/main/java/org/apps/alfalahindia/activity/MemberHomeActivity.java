package org.apps.alfalahindia.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.apps.alfalahindia.Managers.ALIFFragmentManager;
import org.apps.alfalahindia.R;
import org.apps.alfalahindia.Util.PrefKeys;
import org.apps.alfalahindia.Util.ProgressBarHandler;
import org.apps.alfalahindia.Util.ToastUtil;
import org.apps.alfalahindia.fragment.DashboardFragment;
import org.apps.alfalahindia.fragment.MembersListFragment;
import org.apps.alfalahindia.fragment.ObjectivesFragment;
import org.apps.alfalahindia.fragment.SettingsFragment;
import org.apps.alfalahindia.pojo.Member;
import org.apps.alfalahindia.rest.ALIFResponse;
import org.apps.alfalahindia.rest.JsonParser;
import org.apps.alfalahindia.rest.RequestMethod;
import org.apps.alfalahindia.rest.RestURI;
import org.apps.alfalahindia.volley.ALIFStringRequest;

import java.util.HashMap;
import java.util.Map;

public class MemberHomeActivity extends BaseActivity {

    ProgressBarHandler progressBarHandler = null;
    Member member;
    private String TAG = MemberHomeActivity.class.getSimpleName();
    private MenuItem activeMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String username = prefs.getString(PrefKeys.MEMBER_USER_NAME);
        String authCode = prefs.getString(PrefKeys.USER_AUTH_TOKEN);
        requestData(username, authCode);
    }

    private void requestData(String username, String authCode) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        progressBarHandler = new ProgressBarHandler(this);

        Map<String, String> params = new HashMap<>();
        params.put("authCode", authCode);
        params.put("username", username);
        String uri = RestURI.getUri("/member/get/", params);

        progressBarHandler.show();
        ALIFStringRequest request = new ALIFStringRequest(RequestMethod.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ALIFResponse alifResponse = JsonParser.fromJson(response, ALIFResponse.class);
                        member = JsonParser.fromJson(JsonParser.toJson(alifResponse.getData()), Member.class);
                        progressBarHandler.hide();
                        prepareHomePage(member.getRole());
                        activeMenuItem = navigationView.getMenu().getItem(0);
                        activeMenuItem.setChecked(true);

                        if (fragmentManager.getFragmentManager().getBackStackEntryCount() >= 1) {
                            fragmentManager.replaceFragment(R.id.content_frame, DashboardFragment.newInstance(member));
                        } else {
                            fragmentManager.addFragment(R.id.content_frame, DashboardFragment.newInstance(member));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastUtil.toast(getApplicationContext(), error.getMessage());
                        progressBarHandler.hide();
                    }
                }
        );
        requestQueue.add(request);
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

        Fragment fragment = null;

        switch (id) {
            case R.id.nav_gallery:
                break;
            case R.id.nav_manage:
                fragment = new SettingsFragment();
                break;
            case R.id.nav_dashboard:
                fragment = DashboardFragment.newInstance(member);
                break;
            case R.id.nav_members_list:
                fragment = new MembersListFragment();
                break;
            case R.id.nav_objectives:
                fragment = new ObjectivesFragment();
                break;
            case R.id.nav_logout:
                prefs.setString(PrefKeys.USER_AUTH_TOKEN, null);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                this.finish();
                break;
            default:
                break;
        }

        if (fragment != null) {
            ALIFFragmentManager fragmentManager = new ALIFFragmentManager(this);
            String backStateName = fragment.getClass().getName();
            fragmentManager.replaceFragment(R.id.content_frame, fragment);

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
