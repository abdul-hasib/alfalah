package org.apps.alfalahindia.fragment.member;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apps.alfalahindia.Managers.DatetimeManager;
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

public class MemberCreateFragment extends MemberManageFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = super.onCreateView(inflater, container, savedInstanceState);


        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatetimeManager datetimeManager = new DatetimeManager(dateText, getActivity());
                datetimeManager.onFocusChange(view, true);
            }
        });

        addMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateParameters() && ConnectionDetector.isOnline()) {
                    createMember();
                }
            }
        });

        return view;
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
                        ToastUtil.toast(response);
                        progressBarHandler.hide();
                        getActivity().getFragmentManager().popBackStack();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            ALIFResponse ALIFResponse = new Gson().fromJson(error.getMessage(), ALIFResponse.class);
                            ToastUtil.toast(ALIFResponse.getMessage());
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
                member.setAuthCode(Prefs.getString(PrefKeys.USER_AUTH_TOKEN));

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
