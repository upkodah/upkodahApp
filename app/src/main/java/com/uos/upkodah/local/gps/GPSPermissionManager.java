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

    @SuppressLint("MissingPermission")
    public void requestCurrentPosition(Context context, LocationListener listener){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = manager.getAllProviders();

        boolean isProvided = false;
        String bestProvider = "";
        for(String provider : providers){
            if(manager.isProviderEnabled(provider)){
                isProvided = true;

                bestProvider = provider;
                if(bestProvider.equals(LocationManager.GPS_PROVIDER)) break;
            }
        }

        // 어떤 Provider도 제공받지 못하면 오류 메시지 출력
        if(!isProvided || bestProvider.isEmpty())
            Toast.makeText(context, "GPS 연결을 확인하세요",Toast.LENGTH_SHORT).show();
        else{
            manager.requestLocationUpdates(bestProvider, 0,0,listener);
        }
    }
}
