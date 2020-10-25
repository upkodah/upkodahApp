package com.uos.upkodah.local.map.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;

import net.daum.mf.map.api.MapView;

public class KakaoMapFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        MapView mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = (ViewGroup) inflater.inflate(R.layout.map_frag, container, false);

        mapViewContainer.addView(mapView);

        return mapViewContainer;
    }
}
