package com.uos.upkodah.local.gps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.uos.upkodah.local.position.PositionInformation;

import java.util.ArrayList;
import java.util.List;

public class GPSPermissionManager {
    private final static String[] permissions = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static GPSPermissionManager gpsPermissionManager;
    public static GPSPermissionManager getInstance(Context context){
        // 권한이 없으면 null 반환
        if(!isPermitted(context)) return null;

        if(gpsPermissionManager == null) gpsPermissionManager = new GPSPermissionManager();

        return gpsPermissionManager;
    }

    private GPSPermissionManager(){}

    public static boolean isPermitted(Context context){
        return ContextCompat.checkSelfPermission(context, permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, permissions[1]) == PackageManager.PERMISSION_GRANTED;
    }
    public static void requestPermission(FragmentActivity activity){
        if(!isPermitted(activity)) activity.requestPermissions(permissions,0);
    }
    public static String[] getPermissions(){
        return permissions;
    }

    @SuppressLint("MissingPermission")
    public void requestCurrentPosition(Context context, LocationListener listener){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = manager.getProviders(true);

        // provider에는 GPS, Network, Passive가 존재한다.
        // GPS는 1번, Network는 2번, Passive는 3번이다. 일단 3번은 고려하지 않는다.

        boolean isProvided = false;
        String bestProvider = "";
        for(String provider : providers){
            if(manager.isProviderEnabled(provider)){
                manager.requestLocationUpdates(provider, 5000,5,listener);
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void removeGPSRequest(Context context, LocationListener listener){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        manager.removeUpdates(listener);
    }
}
