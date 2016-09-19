package org.apps.alfalahindia.fragment.member;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import org.apps.alfalahindia.Managers.DatetimeManager;
import org.apps.alfalahindia.R;
import org.apps.alfalahindia.Util.MemberUtil;
import org.apps.alfalahindia.Util.PrefKeys;
import org.apps.alfalahindia.Util.Prefs;
import org.apps.alfalahindia.Util.ProgressBarHandler;
import org.apps.alfalahindia.enums.MemberType;
import org.apps.alfalahindia.fragment.BaseFragment;
import org.apps.alfalahindia.pojo.Member;

public class MemberManageFragment extends BaseFragment {

    EditText nameText;
    EditText emailText;
    EditText mobileText;
    EditText usernameText;
    EditText dateText;
    EditText addressText;
    EditText placeText;
    EditText pincodeText;
    EditText dojText;
    TextView title;
    Button addMemberBtn;
    Switch memberType;

    ProgressBarHandler progressBarHandler = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_member_create, container, false);

        nameText = (EditText) view.findViewById(R.id.input_name);
        emailText = (EditText) view.findViewById(R.id.input_email);
        mobileText = (EditText) view.findViewById(R.id.input_mobile);
        usernameText = (EditText) view.findViewById(R.id.input_username);
        addMemberBtn = (Button) view.findViewById(R.id.btn_add_member);
        memberType = (Switch) view.findViewById(R.id.switch_member_type);
        dateText = (EditText) view.findViewById(R.id.input_date);
        addressText = (EditText) view.findViewById(R.id.input_address);
        placeText = (EditText) view.findViewById(R.id.input_place);
        pincodeText = (EditText) view.findViewById(R.id.input_pincode);
        title = (TextView) view.findViewById(R.id.register_member_title);


        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatetimeManager datetimeManager = new DatetimeManager(dateText, getActivity());
                datetimeManager.onFocusChange(view, true);
            }
        });

        return view;
    }

    public boolean validateParameters() {

        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String username = usernameText.getText().toString();

        if (name.isEmpty()) {
            nameText.setError("Name can not be blank");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Email is not valid");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() < 10 || mobile.length() > 10) {
            mobileText.setError("Mobile number is not valid (enter without country code");
            valid = false;
        } else {
            mobileText.setError(null);
        }

        if (username.isEmpty()) {
            usernameText.setError("ALIF Id can not be blank");
            valid = false;
        } else {
            usernameText.setError(null);
        }

        return valid;
    }

    public Member getMemberDetails(){

        Member member = new Member();

        member.setAuthCode(Prefs.getString(PrefKeys.USER_AUTH_TOKEN));
        member.setUsername(usernameText.getText().toString());
        member.setName(nameText.getText().toString());
        member.setEmail(emailText.getText().toString());
        member.setMobile(mobileText.getText().toString());
        member.setAddress(addressText.getText().toString());
        member.setPlace(placeText.getText().toString());
        member.setPincode(pincodeText.getText().toString());
        member.setJoiningDate(dateText.getText().toString());

        if (memberType.isChecked()) {
            member.setMembership(MemberType.LIFETIME);
        } else {
            member.setMembership(MemberType.REGULAR);
        }

        return member;
    }

    public void loadMemberDetailsToUI(Member member) {

        usernameText.setText(member.getUsername());
        nameText.setText(member.getName());
        mobileText.setText(member.getMobile());
        emailText.setText(member.getEmail());
        addressText.setText(member.getAddress());
        pincodeText.setText(member.getPincode());
        placeText.setText(member.getPlace());
        dateText.setText(member.getJoiningDate());
        dateText.setEnabled(true);
        memberType.setEnabled(true);

        if (member.getMembership() == MemberType.LIFETIME) {
            memberType.setChecked(true);
        } else {
            this.memberType.setChecked(false);
        }

        if (!MemberUtil.isSuperAdmin()) {
            dateText.setEnabled(false);
            memberType.setEnabled(false);

        }

    }
}
