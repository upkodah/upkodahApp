package com.uos.upkodah.fragment.map.data;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uos.upkodah.data.local.gps.GeoCoordinate;
import com.uos.upkodah.data.local.gps.GeoCoordinateUtil;
import com.uos.upkodah.fragment.map.GoogleMapDrawable;
import com.uos.upkodah.fragment.map.listener.MarkerListener;
import com.uos.upkodah.fragment.map.listener.ZoomListener;
import com.uos.upkodah.util.BitmapIconManager;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapData implements GeoCoordinate {
    public GoogleMapData(){
        // 초기화
        //this.mapMarkers = new ArrayList<>();
        setCenter(GeoCoordinateUtil.getInit());
        setZoomLevelWithDepth(1);
    }

    private List<GoogleMapDrawable> mapMarkers = null;
    public List<? extends GoogleMapDrawable> getMapMarkers() {
        return mapMarkers;
    }
    public void setMapMarkers(List<? extends GoogleMapDrawable> mapMarkers) {
        this.mapMarkers = new ArrayList<>(mapMarkers);
        mapListener.updateMarker();
    }
    public void addMapMarker(GoogleMapDrawable mapMarker){
        if(this.mapMarkers==null) this.mapMarkers = new ArrayList<>();
        this.mapMarkers.add(mapMarker);
        mapListener.updateMarker();
    }

    public final static float[] ZOOM_DEPTH = {12f, 15f, 18f};
    private float zoomLevel = ZOOM_DEPTH[1];
    private int currentDepth;
    public float getZoomLevel() {
        return mapListener.googleMap.getCameraPosition().zoom;
    }
    public void setZoomLevel(float zoomLevel) {
        this.zoomLevel = zoomLevel;
        mapListener.position = new CameraPosition.Builder().target(center).zoom(zoomLevel).build();
        mapListener.updateCamera();
    }
    public void setZoomLevelWithDepth(int depth){
        float quarter = (ZOOM_DEPTH[2]-ZOOM_DEPTH[0])/4;
        setZoomLevel(ZOOM_DEPTH[depth-1]-quarter);
    }
    public int getZoomDepth(){
        return currentDepth;
    }


    private LatLng center;
    public void setCenter(GeoCoordinate coordinate){
        this.center = GeoCoordinateUtil.toLatLng(coordinate);
        mapListener.position = new CameraPosition.Builder().target(center).zoom(zoomLevel).build();
        mapListener.updateCamera();
    }
    /**
     * 마커 평균으로 중심 좌표를 설정합니다,
     */
    public void setCenterUsingPositions(){
        // 평균 위도와 경도. 기본값 설정 : 전체 지점들의 평균값으로 결정
        if(mapMarkers != null && mapMarkers.size()>0){
            GeoCoordinate[] coords = new GeoCoordinate[mapMarkers.size()];
            mapMarkers.toArray(coords);
            this.setCenter(GeoCoordinateUtil.average(coords));
        }
    }
    @Override
    public double getLongitude() {
        return center.longitude;
    }
    @Override
    public double getLatitude() {
        return center.latitude;
    }


    public void setControllable(boolean controllable){
        mapListener.controllable = controllable;
        if(mapListener.googleMap != null) mapListener.googleMap.getUiSettings().setAllGesturesEnabled(controllable);
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

    private class GoogleMapListener implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnMapClickListener {
        private ZoomListener zoomListener = null;
        private MarkerListener markerListener = null;
        private GoogleMap googleMap;
        private CameraPosition position;
        private boolean controllable = true;

        @Override
        public void onMapReady(GoogleMap googleMap) {
            this.googleMap = googleMap;
            googleMap.setOnCameraMoveListener(this);
            googleMap.setOnMarkerClickListener(this);
            googleMap.setOnInfoWindowClickListener(this);
            googleMap.getUiSettings().setAllGesturesEnabled(controllable);

            updateCamera();
            updateMarker();
        }

        public void updateMarker(){
            try{
                googleMap.clear();

                Log.d("MAP", "표출할 마커 수 : "+mapMarkers.size());
                for(GoogleMapDrawable drawable : mapMarkers){
                    Marker marker =  googleMap.addMarker(new MarkerOptions().position(GeoCoordinateUtil.toLatLng(drawable))
                            .title(drawable.getMarkerWindowTitle())
                            .snippet(drawable.getMarkerWindowSnippet()));
                    // 비트맵 정보가 null이 아니면 기본 아이콘이 아니라 지정된 아이콘으로 설정
                    if(drawable.getIconBitmapKey()!=null && !drawable.getIconBitmapKey().isEmpty()) {
                        Log.d("MAP", "마커 아이콘 키 : " + drawable.getIconBitmapKey());
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(
                                BitmapIconManager.getInstance().get(drawable.getIconBitmapKey())
                        ));
                    }

                    // 마커에 들어갈 데이터 설정
                    marker.setTag(drawable);
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
            marker.showInfoWindow();
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
            System.out.println("마커윈도누름");
            if(markerListener != null){
                markerListener.onMarkerBalloonSelected(marker.getTag());
            }
            else{
            }
        }

        @Override
        public void onCameraMove() {

            position = googleMap.getCameraPosition();
            Log.d("MAP", "현재 위치 : "+position.target.longitude+position.target.latitude);
            zoomLevel = position.zoom;
            int result = 1;
            float zoom = getZoomLevel();
            for(float f : ZOOM_DEPTH){
                if(zoom>f) {
                    ++result;
                }
            }
            currentDepth = result;
            if(result>3) currentDepth=3;
            Log.d("MAP", "현재 줌 : "+currentDepth);
            center = position.target;
            if(zoomListener != null) zoomListener.onZoomChanged(googleMap.getCameraPosition().zoom);
        }

        @Override
        public void onMapClick(LatLng latLng) {

        }
    }
}

