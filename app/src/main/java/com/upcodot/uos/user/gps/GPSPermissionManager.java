package com.upcodot.uos.user.gps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.upcodot.uos.user.input.PositionInformation;

public class GPSPermissionManager {
    private final static String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static GPSPermissionManager gpsPermissionManager;
    public static GPSPermissionManager getInstance(Context context){
        // 권한이 없으면 null 반환
        if(!isPermitted(context)) return null;

        if(gpsPermissionManager == null) gpsPermissionManager = new GPSPermissionManager();

        return gpsPermissionManager;
    }

    private GPSPermissionManager(){
        // GPS 권한을 요청합니다.

    }
    public static boolean isPermitted(Context context){
        return ContextCompat.checkSelfPermission(context, permissions[0]) == PackageManager.PERMISSION_GRANTED;
    }
    public static void requestPermission(FragmentActivity activity){
        if(!isPermitted(activity)) activity.requestPermissions(permissions,0);
    }

    @SuppressLint("MissingPermission")
    public PositionInformation getCurrentPosition(Context context){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return new PositionInformation(location.getLongitude(), location.getLatitude());
    }
}
