package org.apps.alfalahindia.fragment;

import android.os.Bundle;
import android.view.Gravity;
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
        displayDashboardDetails();
        return view;
    }

    private void displayDashboardDetails() {
        dashboardLayout.addView(displayMemberDetails("Member Name", member.getName()));
        dashboardLayout.addView(displayMemberDetails("ALIF Member ID", member.getUsername()));
        dashboardLayout.addView(displayMemberDetails("User Role", member.getRole().toString()));
        dashboardLayout.addView(displayMemberDetails("Email", member.getEmail()));
        dashboardLayout.addView(displayMemberDetails("Mobile", member.getMobile()));
    }

    private LinearLayout displayMemberDetails(String key, String value) {
        LinearLayout nameValueLayout = new LinearLayout(getActivity());
        nameValueLayout.setOrientation(LinearLayout.HORIZONTAL);
        nameValueLayout.setWeightSum(2);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;

        TextView keyText = new TextView(getActivity());
        keyText.setPadding(0, 10, 20, 10);
        keyText.setLayoutParams(layoutParams);
        keyText.setGravity(Gravity.RIGHT);

        TextView valueText = new TextView(getActivity());
        valueText.setPadding(20, 0, 0, 10);
        valueText.setLayoutParams(layoutParams);
        valueText.setGravity(Gravity.LEFT);

        keyText.setText(key);
        valueText.setText(value);

        nameValueLayout.addView(keyText);
        nameValueLayout.addView(valueText);

        return nameValueLayout;
    }

}
