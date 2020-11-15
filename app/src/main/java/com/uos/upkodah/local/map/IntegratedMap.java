package com.uos.upkodah.local.map;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;

import net.daum.mf.map.api.MapView;

public class IntegratedMap {
    public final static int TYPE_GOOGLE = 0;
    public final static int TYPE_KAKAO = 1;

    private final int type;

    private GoogleMap googleMap = null;
    private MapView kakaoMap = null;

    public IntegratedMap(GoogleMap map){
        this.googleMap = map;
        type = TYPE_GOOGLE;
    }
    public IntegratedMap(MapView map){
        this.kakaoMap = map;
        type = TYPE_KAKAO;
    }

    public void setZoom(float zoom){
        switch(type){
            case TYPE_GOOGLE:
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                    .zoom(zoom)
                    .build()));
            case TYPE_KAKAO:
                kakaoMap.setZoomLevelFloat(zoom, true);
        }
    }

}
