package org.apps.alfalahindia.fragment.member;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apps.alfalahindia.Util.ConnectionDetector;
import org.apps.alfalahindia.Util.MemberUtil;
import org.apps.alfalahindia.Util.ToastUtil;
import org.apps.alfalahindia.enums.MemberType;
import org.apps.alfalahindia.pojo.Member;
import org.apps.alfalahindia.rest.JsonParser;

/**
 * Created by bmi on 9/17/2016.
 */
public class MemberUpdateFragment extends MemberManageFragment {

    private static String FRAGMENT_ARGUMENT = "FRAGMENT_ARGUMENT";
    Member member;

    public static MemberUpdateFragment newInstance(Member member) {
        MemberUpdateFragment fragment = new MemberUpdateFragment();
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

        final View view = super.onCreateView(inflater, container, savedInstanceState);

        this.usernameText.setEnabled(false);
        this.dateText.setEnabled(false);
        this.addMemberBtn.setText("Update");
        title.setText("Update Member");

        loadMember(member);

        addMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateParameters() && ConnectionDetector.isOnline()) {
                    updateMember(member);
                }
            }
        });

        return view;
    }

    private void updateMember(Member member) {

        ToastUtil.toast("Update code here");
    }

    private void loadMember(Member member) {

        this.usernameText.setText(member.getUsername());
        this.nameText.setText(member.getName());
        this.mobileText.setText(member.getMobile());
        this.emailText.setText(member.getEmail());
        this.addressText.setText(member.getAddress());
        this.pincodeText.setText(member.getPincode());
        this.placeText.setText(member.getPlace());
        this.dateText.setText(member.getJoiningDate());

        if (member.getMemberType() == MemberType.LIFETIME) {
            this.memberType.setChecked(true);
        } else {
            this.memberType.setChecked(false);
        }

        if (!MemberUtil.isSuperAdmin()) {
            this.dateText.setEnabled(false);
            this.memberType.setEnabled(false);

        }


    }


}
