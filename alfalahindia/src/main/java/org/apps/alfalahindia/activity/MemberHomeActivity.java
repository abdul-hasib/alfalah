package org.apps.alfalahindia.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.Util.PrefKeys;
import org.apps.alfalahindia.Util.ProgressBarHandler;
import org.apps.alfalahindia.Util.ToastUtil;
import org.apps.alfalahindia.fragment.DashboardFragment;
import org.apps.alfalahindia.fragment.MembersListFragment;
import org.apps.alfalahindia.fragment.ObjectivesFragment;
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
                        fragmentManager.addFragment(R.id.content_frame, DashboardFragment.newInstance(member.toString()));
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
                fragment = DashboardFragment.newInstance(member.toString());
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
