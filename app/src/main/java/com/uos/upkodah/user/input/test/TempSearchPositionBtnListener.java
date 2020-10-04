package com.uos.upkodah.user.input.test;

import androidx.fragment.app.FragmentActivity;

import com.uos.upkodah.user.gps.GPSPermissionManager;
import com.uos.upkodah.user.input.MainUserSearchInput;
import com.uos.upkodah.user.input.SearchPositionBtnListener;

public class TempSearchPositionBtnListener extends SearchPositionBtnListener {
    private MainUserSearchInput positionInput;

    public TempSearchPositionBtnListener(FragmentActivity activity, MainUserSearchInput positionInput) {
        super(activity);
        this.positionInput = positionInput;
    }

    @Override
    public void bringLocation() {
        // 위치정보를 가져오면 그걸 그냥 그대로 넣음
        positionInput.setPosition(GPSPermissionManager.getInstance(activity).getCurrentPosition(activity));
    }
}
