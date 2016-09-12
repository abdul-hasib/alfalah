package org.apps.alfalahindia.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.pojo.Member;
import org.apps.alfalahindia.rest.JsonParser;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends BaseFragment {
    private static final String ARG_MEMBER = "member";

    TextView nameText;
    TextView usernameText;
    TextView roleText;
    TextView emailText;
    TextView mobileText;

    private String memberDetails;

    private OnFragmentInteractionListener mListener;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param member Parameter 1.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String member) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MEMBER, member);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            memberDetails = getArguments().getString(ARG_MEMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        Member member = JsonParser.fromJson(memberDetails, Member.class);

        LinearLayout dashboardLayout = (LinearLayout) view.findViewById(R.id.dashBoardLayout);

        dashboardLayout.addView(displayMemberDetails("Member Name", member.getName()));
        dashboardLayout.addView(displayMemberDetails("ALIF Member ID", member.getUsername()));
        dashboardLayout.addView(displayMemberDetails("User Role", member.getRole().toString()));
        dashboardLayout.addView(displayMemberDetails("Email", member.getEmail()));
        dashboardLayout.addView(displayMemberDetails("Mobile", member.getMobile()));

        return view;
    }

    private LinearLayout displayMemberDetails(String key, String value) {
        LinearLayout nameValueLayout = new LinearLayout(getActivity());
        nameValueLayout.setOrientation(LinearLayout.HORIZONTAL);
        nameValueLayout.setWeightSum(2);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;

        TextView keyText = new TextView(getActivity());
        keyText.setPadding(0, 10, 20, 10);
        keyText.setLayoutParams(layoutParams);
        keyText.setGravity(Gravity.RIGHT);

        TextView valueText = new TextView(getActivity());
        valueText.setPadding(20, 0, 0, 10);
        valueText.setLayoutParams(layoutParams);
        valueText.setGravity(Gravity.LEFT);

        keyText.setText(key);
        valueText.setText(value);

        nameValueLayout.addView(keyText);
        nameValueLayout.addView(valueText);

        return nameValueLayout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
