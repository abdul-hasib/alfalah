package org.apps.alfalahindia.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.Util.ConnectionDetector;
import org.apps.alfalahindia.Util.PrefKeys;
import org.apps.alfalahindia.Util.ToastUtil;
import org.json.JSONObject;

public class MemberRegisterFragment extends BaseFragment {
    EditText nameText;
    EditText emailText;
    EditText mobileText;
    EditText usernameText;
    EditText passwordText;
    EditText confirmPasswordText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_register, container, false);

        nameText = (EditText) view.findViewById(R.id.input_name);
        emailText = (EditText) view.findViewById(R.id.input_email);
        mobileText = (EditText) view.findViewById(R.id.input_mobile);
        usernameText = (EditText) view.findViewById(R.id.input_username);
        passwordText = (EditText) view.findViewById(R.id.input_password);
        confirmPasswordText = (EditText) view.findViewById(R.id.input_confirm_password);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_member_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save_member:
                if (validateParameters() && ConnectionDetector.isOnline(getActivity())) {
                    activateMember();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validateParameters() {

        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();

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

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("Must be between 4 and 10 characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (!passwordText.getText().toString().equals(confirmPassword)) {
            confirmPasswordText.setError("Passwords don't match");
            valid = false;
        } else {
            confirmPasswordText.setError(null);
        }

        return valid;
    }

    private void activateMember() {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        String uri = "/member/getall/";

        JsonRequest request = new JsonObjectRequest(Request.Method.POST, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        prefs.setBoolean(PrefKeys.USER_ACCOUNT_ACTIVATED, true);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastUtil.toast(getActivity(), "Error occurred during activation: " + error.getMessage());
                    }
                });

        requestQueue.add(request);
    }

    @Override
    public void onDetach() {

        prefs.setBoolean(PrefKeys.USER_ACCOUNT_ACTIVATED, true);
        super.onDetach();
    }
}
