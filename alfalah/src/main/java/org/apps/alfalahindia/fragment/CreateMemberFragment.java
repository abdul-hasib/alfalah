package org.apps.alfalahindia.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import org.apps.alfalahindia.Util.ProgressBarHandler;
import org.apps.alfalahindia.Util.ToastUtil;
import org.apps.alfalahindia.pojo.Member;
import org.apps.alfalahindia.rest.RequestMethod;
import org.apps.alfalahindia.rest.RestHelper;
import org.apps.alfalahindia.rest.RestResponse;
import org.apps.alfalahindia.volley.ALIFStringRequest;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CreateMemberFragment extends Fragment {

    EditText name;
    EditText email;
    EditText mobile;
    EditText username;
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

        name = (EditText) view.findViewById(R.id.input_name);
        email = (EditText) view.findViewById(R.id.input_email);
        mobile = (EditText) view.findViewById(R.id.input_mobile);
        username = (EditText) view.findViewById(R.id.input_username);
        role = (Switch) view.findViewById(R.id.input_role);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_member_create, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save_member:
                if (validateParameters()) {
                    createMember();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean validateParameters() {

        if (name.getText().toString().isEmpty()
                || email.getText().toString().isEmpty()
                || mobile.getText().toString().isEmpty()
                || username.getText().toString().isEmpty()) {

            ToastUtil.toast(getActivity(), "Please enter details");
            return false;
        }

        return true;
    }

    private void createMember() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        progressBarHandler = new ProgressBarHandler(this.getActivity());
        String uri = RestHelper.getUri("/member/create/");

        progressBarHandler.show();
        ALIFStringRequest request = new ALIFStringRequest(RequestMethod.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ToastUtil.toast(getActivity().getApplicationContext(), response);
                        progressBarHandler.hide();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            RestResponse restResponse = new Gson().fromJson(error.getMessage(), RestResponse.class);
                            ToastUtil.toast(getActivity().getApplicationContext(), restResponse.getResponse());
                        }
                        progressBarHandler.hide();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                System.out.println("Calling get params");
                Member member = new Member();
                member.setName(name.getText().toString());
                member.setEmail(email.getText().toString());
                member.setMobile(mobile.getText().toString());
                member.setId(username.getText().toString());

                if (role.isChecked()) {
                    member.setRole(Member.Role.ADMIN);
                } else {
                    member.setRole(Member.Role.MEMBER);
                }

                Map<String, String> params = new HashMap<String, String>();
                Type type = new TypeToken<Map<String, String>>() {
                }.getType();

                Gson gson = new Gson();
                return gson.fromJson(gson.toJson(member), type);
            }
        };

        requestQueue.add(request);
    }
}
