package com.uos.upkodah.dialog.permission;

import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.uos.upkodah.local.gps.GPSPermissionManager;

public class PermissionRequiringOnClickListener implements View.OnClickListener{
    private FragmentActivity activity;
    private String[] permissions;
    private View.OnClickListener listener;

    public PermissionRequiringOnClickListener( View.OnClickListener listener, FragmentActivity activity, String...permissions){
        this.activity = activity;
        this.permissions = permissions;
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(checkAllPermitted()){
            listener.onClick(view);
        }
        else{
            Toast.makeText(view.getContext(), "권한이 필요합니다.",Toast.LENGTH_SHORT).show();
            activity.requestPermissions(permissions, 1);
        }
    }

    private boolean checkAllPermitted(){
        boolean result = true;

        for(String permission : permissions){
            result &= (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED);
        }
        return result;
    }
}
