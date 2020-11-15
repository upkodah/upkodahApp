package com.uos.upkodah.local.map.google.data;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.uos.upkodah.BR;
import com.uos.upkodah.local.map.MapDrawable;
import com.uos.upkodah.local.map.google.GoogleMapDrawable;
import com.uos.upkodah.local.map.listener.MarkerListener;
import com.uos.upkodah.local.map.listener.ZoomListener;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapData extends BaseObservable {
    public GoogleMapData(){
        // 초기화
        setCenter(37.4, 122.1);
        setZoomLevelWithDepth(1);
    }

    private List<GoogleMapDrawable> mapMarkers = null;
    public List<? extends GoogleMapDrawable> getMapMarkers() {
        return mapMarkers;
    }
    public void setMapMarkers(List<? extends GoogleMapDrawable> mapMarkers) {
        this.mapMarkers = new ArrayList<>(mapMarkers);
        mapListener.updateMarker();
        setCenterUsingPositions();
    }

    public final static float[] ZOOM_DEPTH = {10f, 20f, 30f};
    private float zoomLevel = ZOOM_DEPTH[1];
    public float getZoomLevel() {
        return mapListener.googleMap.getCameraPosition().zoom;
    }
    public void setZoomLevel(float zoomLevel) {
        this.zoomLevel = zoomLevel;
        mapListener.position = new CameraPosition.Builder().target(center).zoom(zoomLevel).build();
        mapListener.updateCamera();
    }
    public void setZoomLevelWithDepth(int level){
        setZoomLevel(ZOOM_DEPTH[level-1]);
    }
    public int getZoomDepth(){
        int result = 1;
        float zoom = getZoomLevel();
        for(float f : ZOOM_DEPTH){
            if(zoom<=f) return result;
            result++;
        }
        return 3;
    }

    private LatLng center = new LatLng(37.4, 122.1);
    public void setCenter(double centerLongitude, double centerLatitude){
        this.center = new LatLng(centerLatitude, centerLongitude);
        mapListener.position = new CameraPosition.Builder().target(center).zoom(zoomLevel).build();
        mapListener.updateCamera();
    }
    /**
     * 마커 평균으로 중심 좌표를 설정합니다,
     */
    public void setCenterUsingPositions(){
        // 평균 위도와 경도. 기본값 설정 : 전체 지점들의 평균값으로 결정
        if(mapMarkers != null){
            double avgLongitude = 0;
            double avgLatitude = 0;

            if(mapMarkers.size()==0) return;

            for(MapDrawable m : mapMarkers){
                avgLongitude += m.getLongitude();
                avgLatitude += m.getLatitude();
            }
            avgLongitude /= mapMarkers.size();
            avgLatitude /= mapMarkers.size();

            System.out.println(avgLongitude+","+avgLatitude);
            setCenter(avgLongitude, avgLatitude);
        }
    }



    private GoogleMapListener mapListener = new GoogleMapListener();
    /**
     * 줌 리스너를 설정합니다.
     */
    public void setZoomListener(@Nullable ZoomListener zoomListener){
        mapListener.zoomListener = zoomListener;
    }
    /**
     * 마커 리스너를 설정합니다.
     */
    public void setMarkerListener(@Nullable MarkerListener listener){
        mapListener.markerListener = listener;
    }
    public void setListenerAndSyncInto(MapView mapView){
        mapView.getMapAsync(mapListener);
    }

    private class GoogleMapListener implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnCameraMoveListener {
        private ZoomListener zoomListener = null;
        private MarkerListener markerListener = null;
        private GoogleMap googleMap;
        private CameraPosition position;

        @Override
        public void onMapReady(GoogleMap googleMap) {
            this.googleMap = googleMap;
            googleMap.setOnMarkerClickListener(this);

            updateCamera();
            updateMarker();
        }

        public void updateMarker(){
            try{
                googleMap.clear();
                for(GoogleMapDrawable drawable : mapMarkers){

                    drawable.drawInto(googleMap);
                }
            }
            catch(NullPointerException e){
                Log.d("MAP", "맵 준비중");
            }

        }
        public void updateCamera(){
            try{
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
            }
            catch(NullPointerException e){
                Log.d("MAP", "맵 준비중");
            }
        }

        @Override
        public boolean onMarkerClick(Marker marker) {
            System.out.println("마커누름");
            if(markerListener != null){
                markerListener.onMarkerSelected(marker.getTag());
                return true;
            }
            else{
                return false;
            }
        }

        @Override
        public void onInfoWindowClick(Marker marker) {
            if(markerListener != null){
                markerListener.onMarkerBalloonSelected(marker.getTag());
            }
            else{
            }
        }

        @Override
        public void onCameraMove() {
            if(zoomListener != null) zoomListener.onZoomChanged(googleMap.getCameraPosition().zoom);
            position = googleMap.getCameraPosition();
            zoomLevel = position.zoom;
            center = position.target;
        }
    }
}

