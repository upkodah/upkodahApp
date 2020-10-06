package com.uos.upkodah.user.input;

import android.content.Intent;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.uos.upkodah.local.SelectingLocationActivity;
import com.uos.upkodah.local.gps.GPSPermissionManager;
import com.uos.upkodah.local.gps.PositionInformation;

public class SearchLocationBtnListener implements View.OnClickListener{
    // 이 버튼을 누르기 전에는 반드시 권한을 확인해야합니다.
    protected FragmentActivity activity;
    public SearchLocationBtnListener(FragmentActivity activity){
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

    private PositionInformation positionInformation;
    public void setPositionInformation(PositionInformation p){
        if(p.getLatitude()==0 && p.getLongitude()==0) positionInformation = null;
        this.positionInformation = p;
    }
    protected Intent getPositionInformationIntent(){
        Intent result = new Intent(activity, SelectingLocationActivity.class);
        if(positionInformation== null) return result;

        result.putExtra(PositionInformation.EXTRA, positionInformation);

        return result;
    }
    public void bringLocation(){
        activity.startActivityForResult(getPositionInformationIntent(),1);
    }
}
