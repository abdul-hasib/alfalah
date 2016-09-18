package org.apps.alfalahindia.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.apps.alfalahindia.Util.ConnectionDetector;
import org.apps.alfalahindia.Util.ToastUtil;

/**
 * Created by bmi on 9/17/2016.
 */
public class UpdateMemberFragment  extends ManageMemberFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = super.onCreateView(inflater, container, savedInstanceState);

        this.usernameText.setEnabled(false);
        this.dateText.setEnabled(false);
        this.addMemberBtn.setText("Update");
        title.setText("Update Member");

        addMemberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateParameters() && ConnectionDetector.isOnline()) {
                    updateMember();
                }
            }
        });

        return view;
    }
    private void updateMember(){

        ToastUtil.toast("Update member here");
    }
}
