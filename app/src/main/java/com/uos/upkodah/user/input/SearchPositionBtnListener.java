package com.uos.upkodah.user.input;

import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.uos.upkodah.user.gps.GPSPermissionManager;

public abstract class SearchPositionBtnListener implements View.OnClickListener{
    // 이 버튼을 누르기 전에는 반드시 권한을 확인해야합니다.
    protected FragmentActivity activity;
    public SearchPositionBtnListener(FragmentActivity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View view){
        GPSPermissionManager gps = GPSPermissionManager.getInstance(activity);
        if(gps==null){
            GPSPermissionManager.requestPermission(activity);
        }
        else{
            bringLocation();
        }
    }

    public abstract void bringLocation();
}
