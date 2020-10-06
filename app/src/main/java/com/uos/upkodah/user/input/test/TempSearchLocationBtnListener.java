package com.uos.upkodah.user.input.test;

import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import com.uos.upkodah.R;
import com.uos.upkodah.local.gps.GPSPermissionManager;
import com.uos.upkodah.local.gps.PositionInformation;
import com.uos.upkodah.user.input.MainUserSearchInput;
import com.uos.upkodah.user.input.SearchLocationBtnListener;

public class TempSearchLocationBtnListener extends SearchLocationBtnListener {
    private MainUserSearchInput positionInput;

    public TempSearchLocationBtnListener(FragmentActivity activity, MainUserSearchInput positionInput) {
        super(activity);
        this.positionInput = positionInput;
    }

    @Override
    public void bringLocation() {
        // 위치정보를 가져오면 그걸 그냥 그대로 넣음
        final PositionInformation p = positionInput.getPositionInformation();
        p.requestGPS(activity, new PositionInformation.Listener() {
            @Override
            public void onResponse() {
                p.requestAddress(activity, new PositionInformation.Listener() {
                    @Override
                    public void onResponse() {
                        EditText postalAddressEdTxt = (EditText) activity.findViewById(R.id.postalAddressEdtxt);
                        postalAddressEdTxt.setText(p.getPostalAddress());
                    }
                });
            }
        });
    }
}
