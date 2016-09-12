package org.apps.alfalahindia.fragment;

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

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.Util.ProgressBarHandler;
import org.apps.alfalahindia.Util.ToastUtil;
import org.apps.alfalahindia.adapters.MemberListAdapter;
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

    private static final String ARG_MEMBER = "member";
    String TAG = MembersListFragment.class.getSimpleName();
    ListView membersListview;
    TextView emptyListview;
    ProgressBarHandler progressBarHandler = null;
    private List<Member> members;
    private String memberDetails;

    public static MembersListFragment newInstance(String member) {
        MembersListFragment fragment = new MembersListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEMBER, member);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_members_list, container, false);
        membersListview = (ListView) view.findViewById(R.id.membersListview);
        emptyListview = (TextView) view.findViewById(R.id.emptyElement);

        setHasOptionsMenu(true);
        requestData();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_member_list, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            memberDetails = getArguments().getString(ARG_MEMBER);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_member:
                ToastUtil.toast(getActivity().getApplicationContext(), "Let's add a member here");
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
        progressBarHandler = new ProgressBarHandler(this.getActivity());

        Map<String, String> params = new HashMap<>();
        Member member = JsonParser.fromJson(memberDetails, Member.class);
        params.put("authCode", member.getAuthCode());

        String uri = RestURI.getUri("/member/list/", params);
        progressBarHandler.show();
        ALIFStringRequest request = new ALIFStringRequest(RequestMethod.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ALIFResponse alifResponse = JsonParser.fromJson(response, ALIFResponse.class);
                        members = Arrays.asList(JsonParser.fromJson(alifResponse.getData().toString(), Member[].class));
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
                        ToastUtil.toast(getActivity().getApplicationContext(), error.getMessage());
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
                        ToastUtil.toast(getActivity(), member.getName());
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
                ToastUtil.toast(getActivity(), "ALIF Id is:  " + member.getUsername().toUpperCase());
            }
        });
    }
}
