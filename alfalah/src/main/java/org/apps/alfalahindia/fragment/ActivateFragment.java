package org.apps.alfalahindia.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apps.alfalahindia.R;
import org.apps.alfalahindia.Util.ConnectionDetector;
import org.apps.alfalahindia.background.BackgroundTask;
import org.apps.alfalahindia.rest.RequestPackage;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActivateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActivateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText name;
    EditText email;
    EditText mobile;
    EditText username;
    EditText password;
    EditText confirmPassword;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    public ActivateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivateFragment newInstance(String param1, String param2) {
        ActivateFragment fragment = new ActivateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activate, container, false);

        name = (EditText) view.findViewById(R.id.loginName);
        email = (EditText) view.findViewById(R.id.loginEmail);
        mobile = (EditText) view.findViewById(R.id.loginMobile);
        username = (EditText) view.findViewById(R.id.loginUsername);
        password = (EditText) view.findViewById(R.id.loginPassword);
        confirmPassword = (EditText) view.findViewById(R.id.loginConfirmPassword);

        Button activate = (Button) view.findViewById(R.id.loginActivate);
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

        if(isEmpty(name) || isEmpty(email) || isEmpty(mobile) || isEmpty(username) || isEmpty(password) || isEmpty(confirmPassword)) {
            Toast.makeText(getActivity(), "Please enter details", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!password.getText().toString().equals(confirmPassword.getText())) {
            Toast.makeText(getActivity(), "Passwords don't match", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private void activateMember() {

        if (validate()) {
            Toast.makeText(getActivity(), "hello call Rest api", Toast.LENGTH_SHORT).show();
        }

        if(ConnectionDetector.isOnline(getActivity())) {
            requestData("/member/role/");
        }
    }

    private void requestData(String uri) {

        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("POST");
        requestPackage.setUri(uri);
        requestPackage.setParam("username", "abdulh");

        BackgroundTask backgroundTask = new BackgroundTask(getActivity());
        backgroundTask.execute(requestPackage);
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
