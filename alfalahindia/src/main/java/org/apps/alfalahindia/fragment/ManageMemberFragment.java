package org.apps.alfalahindia.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apps.alfalahindia.Managers.DatetimeManager;
import org.apps.alfalahindia.R;
import org.apps.alfalahindia.Util.ConnectionDetector;
import org.apps.alfalahindia.Util.PrefKeys;
import org.apps.alfalahindia.Util.Prefs;
import org.apps.alfalahindia.Util.ProgressBarHandler;
import org.apps.alfalahindia.Util.ToastUtil;
import org.apps.alfalahindia.enums.UserRole;
import org.apps.alfalahindia.pojo.Member;
import org.apps.alfalahindia.rest.ALIFResponse;
import org.apps.alfalahindia.rest.JsonParser;
import org.apps.alfalahindia.rest.RequestMethod;
import org.apps.alfalahindia.rest.RestURI;
import org.apps.alfalahindia.volley.ALIFStringRequest;

import java.lang.reflect.Type;
import java.util.Map;

public class ManageMemberFragment extends BaseFragment {

    EditText nameText;
    EditText emailText;
    EditText mobileText;
    EditText usernameText;
    EditText dateText;
    TextView title;
    Button addMemberBtn;
    Switch role;

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
        role = (Switch) view.findViewById(R.id.switch_member_type);
        dateText = (EditText) view.findViewById(R.id.input_date);
        title = (TextView) view.findViewById(R.id.register_member_title);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatetimeManager datetimeManager = new DatetimeManager(dateText);
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
