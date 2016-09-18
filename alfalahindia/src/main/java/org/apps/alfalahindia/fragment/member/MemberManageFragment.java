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
import org.apps.alfalahindia.Util.ProgressBarHandler;
import org.apps.alfalahindia.fragment.BaseFragment;

public class MemberManageFragment extends BaseFragment {

    EditText nameText;
    EditText emailText;
    EditText mobileText;
    EditText usernameText;
    EditText dateText;
    EditText addressText;
    EditText placeText;
    EditText pincodeText;
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

}
