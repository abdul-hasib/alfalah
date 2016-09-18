package org.apps.alfalahindia.fragment.member;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.apps.alfalahindia.Managers.ALIFFragmentManager;
import org.apps.alfalahindia.R;
import org.apps.alfalahindia.Util.MemberUtil;
import org.apps.alfalahindia.Util.PrefKeys;
import org.apps.alfalahindia.Util.Prefs;
import org.apps.alfalahindia.Util.ProgressBarHandler;
import org.apps.alfalahindia.Util.ToastUtil;
import org.apps.alfalahindia.adapters.MemberListAdapter;
import org.apps.alfalahindia.fragment.BaseFragment;
import org.apps.alfalahindia.pojo.Member;
import org.apps.alfalahindia.rest.ALIFResponse;
import org.apps.alfalahindia.rest.JsonParser;
import org.apps.alfalahindia.rest.RequestMethod;
import org.apps.alfalahindia.rest.RestURI;
import org.apps.alfalahindia.volley.ALIFStringRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MembersListFragment extends BaseFragment {

    String TAG = MembersListFragment.class.getSimpleName();
    ListView membersListview;
    TextView emptyListview;
    ProgressBarHandler progressBarHandler = null;
    String authCode;
    private List<Member> members;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_members_list, container, false);
        membersListview = (ListView) view.findViewById(R.id.membersListview);
        emptyListview = (TextView) view.findViewById(R.id.emptyElement);

        setHasOptionsMenu(true);

        authCode = Prefs.getString(PrefKeys.USER_AUTH_TOKEN);
        requestData();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_member_list, menu);

        MenuItem menuItem = menu.findItem(R.id.menu_add_member);
        menuItem.setEnabled(MemberUtil.isAdmin());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_member:
                ToastUtil.toast("Let's add a member here");
                MemberCreateFragment fragment = new MemberCreateFragment();
                getFragmentManager().beginTransaction()
                        .replace(((ViewGroup) getView().getParent()).getId(), fragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.menu_refresh:
                requestData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void requestData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        progressBarHandler = new ProgressBarHandler(getActivity());

        Map<String, String> params = new HashMap<>();
        params.put("authCode", authCode);

        String uri = RestURI.getUri("/member/list/", params);
        progressBarHandler.show();
        ALIFStringRequest request = new ALIFStringRequest(RequestMethod.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ALIFResponse alifResponse = JsonParser.fromJson(response, ALIFResponse.class);
                        members = Arrays.asList(JsonParser.fromJson(JsonParser.toJson(alifResponse.getData()), Member[].class));
                        try {
                            updateDisplay();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressBarHandler.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastUtil.toast(error.getMessage());
                        progressBarHandler.hide();
                    }
                }
        );
        requestQueue.add(request);
    }

    protected void updateDisplay() {
        MemberListAdapter adapter = new MemberListAdapter(getActivity(), members,
                new MemberListAdapter.onSelectedEventCalender() {
                    @Override
                    public void onSelectedEventCalender(Member member, int type) {
                        ToastUtil.toast(member.getName());
                    }
                }
        );

        // MemberAdapter adapter = new MemberAdapter(getActivity(), R.layout.item_member, members);
        membersListview.setAdapter(adapter);
        membersListview.setEmptyView(emptyListview);
        membersListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                System.out.println(membersListview.getItemAtPosition(position).getClass());
                Member member = members.get(position);

                ALIFFragmentManager alifFragmentManager = new ALIFFragmentManager(getActivity());
                System.out.println("Update member");
                alifFragmentManager.replaceFragment(R.id.content_frame, new UpdateMemberFragment());

                ToastUtil.toast("ALIF Id is:  " + member.getUsername().toUpperCase());
            }
        });
    }
}
