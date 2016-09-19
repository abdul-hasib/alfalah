package org.apps.alfalahindia.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apps.alfalahindia.Managers.ALIFFragmentManager;
import org.apps.alfalahindia.R;
import org.apps.alfalahindia.fragment.member.MemberUpdateFragment;
import org.apps.alfalahindia.pojo.Member;
import org.apps.alfalahindia.rest.JsonParser;


public class DashboardFragment extends BaseFragment {
    private static String FRAGMENT_ARGUMENT = "FRAGMENT_ARGUMENT";

    LinearLayout dashboardLayout;
    TextView _nameText;
    TextView _emailText;
    TextView _designationText;
    TextView _usernameText;
    TextView _mobileText;
    TextView _memberTypeText;
    TextView _placeText;
    ImageView _editProfileImage;

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

        _nameText = (TextView) view.findViewById(R.id.user_profile_name);
        _emailText = (TextView) view.findViewById(R.id.user_profile_email);
        _designationText = (TextView) view.findViewById(R.id.user_profile_designation);
        _usernameText = (TextView) view.findViewById(R.id.user_profile_username);
        _mobileText = (TextView) view.findViewById(R.id.user_profile_mobile);
        _memberTypeText = (TextView) view.findViewById(R.id.user_profile_member_type);
        _placeText = (TextView) view.findViewById(R.id.user_profile_place);
        _editProfileImage = (ImageView) view.findViewById(R.id.dashboard_edit_profile);

        _nameText.setText(member.getName());
        _emailText.setText(member.getEmail());
        _designationText.setText(member.getDesignation().getValue());
        _usernameText.setText(member.getUsername());
        _mobileText.setText(member.getMobile());
        _memberTypeText.setText(member.getMembership().toString());
        _placeText.setText(member.getPlace());

        _editProfileImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ALIFFragmentManager alifFragmentManager = new ALIFFragmentManager(getActivity());

                alifFragmentManager.pushFragment(R.id.content_frame, MemberUpdateFragment.newInstance(member));

            }
        });
        return view;
    }


}
