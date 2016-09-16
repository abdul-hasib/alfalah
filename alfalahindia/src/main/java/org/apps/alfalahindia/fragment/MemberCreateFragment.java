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

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.Util.ConnectionDetector;
import org.apps.alfalahindia.Util.PrefKeys;
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

public class MemberCreateFragment extends BaseFragment {

    EditText nameText;
    EditText emailText;
    EditText mobileText;
    EditText usernameText;
    Button signupBtn;
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
        View view = inflater.inflate(R.layout.fragment_member_create, container, false);

        nameText = (EditText) view.findViewById(R.id.input_name);
        emailText = (EditText) view.findViewById(R.id.input_email);
        mobileText = (EditText) view.findViewById(R.id.input_mobile);
        usernameText = (EditText) view.findViewById(R.id.input_username);
        signupBtn = (Button) view.findViewById(R.id.btn_signup);
        role = (Switch) view.findViewById(R.id.input_role);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateParameters() && ConnectionDetector.isOnline(getActivity())) {
                    createMember();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // inflater.inflate(R.menu.menu_member_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save_member:
                if (validateParameters() && ConnectionDetector.isOnline(getActivity())) {
                    createMember();
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

    private void createMember() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        progressBarHandler = new ProgressBarHandler(this.getActivity());
        String uri = RestURI.getUri("/member/create/");

        progressBarHandler.show();
        ALIFStringRequest request = new ALIFStringRequest(RequestMethod.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ToastUtil.toast(getActivity().getApplicationContext(), response);
                        progressBarHandler.hide();
                        getActivity().getFragmentManager().popBackStack();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            ALIFResponse ALIFResponse = new Gson().fromJson(error.getMessage(), ALIFResponse.class);
                            ToastUtil.toast(getActivity().getApplicationContext(), ALIFResponse.getMessage());
                        }
                        progressBarHandler.hide();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Member member = new Member();
                member.setName(nameText.getText().toString());
                member.setEmail(emailText.getText().toString());
                member.setMobile(mobileText.getText().toString());
                member.setUsername(usernameText.getText().toString());
                member.setAuthCode(prefs.getString(PrefKeys.USER_AUTH_TOKEN));

                if (role.isChecked()) {
                    member.setRole(UserRole.ADMIN);
                } else {
                    member.setRole(UserRole.GUEST);
                }

                Type type = new TypeToken<Map<String, String>>() {
                }.getType();

                Gson gson = new Gson();
                System.out.println(gson.fromJson(JsonParser.toJson(member), type).toString());
                return gson.fromJson(JsonParser.toJson(member), type);
            }
        };

        requestQueue.add(request);
    }
}
