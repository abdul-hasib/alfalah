package org.apps.alfalahindia.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.pojo.Member;
import org.apps.alfalahindia.rest.JsonParser;


public class DashboardFragment extends BaseFragment {
    private static String FRAGMENT_ARGUMENT = "FRAGMENT_ARGUMENT";

    LinearLayout dashboardLayout;
    TextView userProfileName, userProfileEmail, userProfileRole, userProfileUsername, userProfileMobile;
    Member member;

    public static DashboardFragment newInstance(Member member) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(FRAGMENT_ARGUMENT, member.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            member = JsonParser.fromJson(getArguments().getString(FRAGMENT_ARGUMENT), Member.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dashboardLayout = (LinearLayout) view.findViewById(R.id.dashBoardLayout);

        userProfileName = (TextView) view.findViewById(R.id.user_profile_name);
        userProfileEmail = (TextView) view.findViewById(R.id.user_profile_email);
        userProfileRole = (TextView) view.findViewById(R.id.user_profile_role);
        userProfileUsername = (TextView) view.findViewById(R.id.user_profile_username);
        userProfileMobile = (TextView) view.findViewById(R.id.user_profile_mobile);

        userProfileName.setText(member.getName());
        userProfileEmail.setText(member.getEmail());
        userProfileRole.setText(member.getRole().toString());
        userProfileUsername.setText(member.getUsername());
        userProfileMobile.setText(member.getMobile());
        return view;
    }

}
