package com.uos.upkodah.fragment.map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.uos.upkodah.R;
import com.uos.upkodah.data.local.gps.GeoCoordinateUtil;
import com.uos.upkodah.databinding.FragmentGoogleMapBinding;
import com.uos.upkodah.fragment.map.data.GoogleMapData;
import com.uos.upkodah.util.BitmapIconManager;

public class GoogleMapFragment extends Fragment {
    private MapView mapView;
    private GoogleMapData data = new GoogleMapData();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentGoogleMapBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_google_map, container, false);

        // 지역 이미지 사전 준비
        Bitmap regionImage = BitmapFactory.decodeResource(getResources(), R.drawable.map_circle).copy(Bitmap.Config.ARGB_8888, true);
        Bitmap dongImage = BitmapFactory.decodeResource(getResources(), R.drawable.map_circle_dong).copy(Bitmap.Config.ARGB_8888, true);

        BitmapIconManager.getInstance().put("__region_gu", regionImage);
        BitmapIconManager.getInstance().put("__region_dong", dongImage);

        mapView = binding.getRoot().findViewById(R.id.map_google);
        mapView.onCreate(savedInstanceState);
        data.setListenerAndSyncInto(mapView);

        return binding.getRoot();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    public void setData(GoogleMapData data){
        this.data = data;
    }
}
