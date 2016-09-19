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

import org.apps.alfalahindia.Util.ConnectionDetector;
import org.apps.alfalahindia.Util.MemberUtil;
import org.apps.alfalahindia.Util.ProgressBarHandler;
import org.apps.alfalahindia.Util.ToastUtil;
import org.apps.alfalahindia.enums.MemberType;
import org.apps.alfalahindia.pojo.Member;
import org.apps.alfalahindia.rest.ALIFResponse;
import org.apps.alfalahindia.rest.JsonParser;
import org.apps.alfalahindia.rest.RequestMethod;
import org.apps.alfalahindia.rest.RestURI;
import org.apps.alfalahindia.volley.ALIFStringRequest;

import java.lang.reflect.Type;
import java.util.Map;

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

        loadMemberDetailsToUI(member);

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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        progressBarHandler = new ProgressBarHandler(this.getActivity());
        String uri = RestURI.getUri("/member/update/");

        progressBarHandler.show();
        ALIFStringRequest request = new ALIFStringRequest(RequestMethod.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ALIFResponse alifResponse = new Gson().fromJson(response, ALIFResponse.class);
                        ToastUtil.toast(alifResponse.getMessage());
                        progressBarHandler.hide();
                        getActivity().getFragmentManager().popBackStack();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            ALIFResponse alifResponse = new Gson().fromJson(error.getMessage(), ALIFResponse.class);
                            ToastUtil.toast(alifResponse.getMessage());
                        }
                        progressBarHandler.hide();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Type type = new TypeToken<Map<String, String>>() {
                }.getType();

                Gson gson = new Gson();
                System.out.println(gson.fromJson(JsonParser.toJson(getMemberDetails()), type).toString());
                return gson.fromJson(JsonParser.toJson(getMemberDetails()), type);
            }
        };

        requestQueue.add(request);

    }


}
