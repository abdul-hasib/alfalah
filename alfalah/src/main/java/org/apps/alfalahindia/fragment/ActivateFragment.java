package org.apps.alfalahindia.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import org.apps.alfalahindia.Util.ToastUtil;
import org.json.JSONObject;

public class ActivateFragment extends Fragment {
    EditText name;
    EditText email;
    EditText mobile;
    EditText username;
    EditText password;
    EditText confirmPassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_register, container, false);

        name = (EditText) view.findViewById(R.id.input_name);
        email = (EditText) view.findViewById(R.id.input_email);
        mobile = (EditText) view.findViewById(R.id.input_mobile);
        username = (EditText) view.findViewById(R.id.input_username);
        password = (EditText) view.findViewById(R.id.input_password);
        confirmPassword = (EditText) view.findViewById(R.id.input_confirm_password);

        Button activate = (Button) view.findViewById(R.id.btn_signup);
        activate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateMember();
            }
        });

        return view;
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    private boolean validate() {

        if (isEmpty(name) || isEmpty(email) || isEmpty(mobile) || isEmpty(username) || isEmpty(password) || isEmpty(confirmPassword)) {
            ToastUtil.toast(getActivity(), "Please enter details");
            return false;
        }

        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            ToastUtil.toast(getActivity(), "Passwords don't match");
            return false;
        }

        return true;
    }

    private void activateMember() {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        if (validate() && ConnectionDetector.isOnline(getActivity())) {

        }

        String uri = "/member/getall/";


        JsonRequest request = new JsonObjectRequest(Request.Method.POST, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(request);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
