package com.uos.upkodah.data.local.map.kakao.fragment.data;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.uos.upkodah.BR;
import com.uos.upkodah.data.local.map.MapDrawable;
import com.uos.upkodah.data.local.map.kakao.KakaoMapDrawable;
import com.uos.upkodah.data.local.map.kakao.UkdMapMarker;
import com.uos.upkodah.data.local.map.kakao.fragment.KakaoMapFragment;
import com.uos.upkodah.fragment.map.listener.MarkerListener;
import com.uos.upkodah.fragment.map.listener.ZoomListener;
import com.uos.upkodah.data.local.position.PositionInformation;
import com.uos.upkodah.util.GeoToMeterConverter;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class KakaoMapData extends BaseObservable{
    private List<KakaoMapDrawable> mapMarkers = null;
    @Bindable
    public List<? extends KakaoMapDrawable> getMapMarkers() {
        return mapMarkers;
    }
    public void setMapMarkers(List<? extends KakaoMapDrawable> mapMarkers) {
        this.mapMarkers = new ArrayList<>(mapMarkers);
        notifyPropertyChanged(BR.mapMarkers);
        setCenterUsingPositions();
    }

    public final static float[] ZOOM_DEPTH = {1.5f, 3.0f, 4.5f};
    private float zoomLevel = 0;
    @Bindable
    public float getZoomLevel() {
        return zoomLevel;
    }
    public void setZoomLevel(float zoomLevel) {
        this.zoomLevel = zoomLevel;
        notifyPropertyChanged(BR.zoomLevel);
    }
    public void setZoomLevelWithDepth(int level){
        setZoomLevel(ZOOM_DEPTH[level]);
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

    private double centerLongitude = 0;
    private double centerLatitude = 0;
    /**
     * 중심좌표를 얻습니다.
     */
    @Bindable
    public double getCenterLongitude() {
        return centerLongitude;
    }
    public void setCenterLongitude(double centerLongitude) {
        this.centerLongitude = centerLongitude;
        notifyPropertyChanged(BR.centerLongitude);
    }

    @Bindable
    public double getCenterLatitude() {
        return centerLatitude;
    }
    public void setCenterLatitude(double centerLatitude) {
        this.centerLatitude = centerLatitude;
        notifyPropertyChanged(BR.centerLatitude);
    }
    public void setCenter(double centerLongitude, double centerLatitude){
        setCenterLongitude(centerLongitude);
        setCenterLatitude(centerLatitude);
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

            System.out.println(this.centerLongitude+" "+this.centerLatitude);;

            setCenterLongitude(avgLongitude);
            setCenterLatitude(avgLatitude);
        }
    }

    private KakaoMapListener mapListener = new KakaoMapListener();
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
    public void setListenerInto(MapView mapView){
        mapView.setMapViewEventListener(mapListener);
        mapView.setPOIItemEventListener(mapListener);
    }

    // 이 클래스는 Map에 적용될 각종 리스너를 관리하는 클래스입니다.
    private class KakaoMapListener implements MapView.MapViewEventListener, MapView.POIItemEventListener {
        ZoomListener zoomListener = null;
        MarkerListener markerListener = null;


        @Override
        public void onMapViewInitialized(MapView mapView) {

        }

        @Override
        public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
            setCenter(mapPoint.getMapPointGeoCoord().longitude, mapPoint.getMapPointGeoCoord().latitude);
        }

        @Override
        public void onMapViewZoomLevelChanged(MapView mapView, int i) {
            if(zoomListener!=null) zoomListener.onZoomChanged(mapView.getZoomLevelFloat());
        }

        @Override
        public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

        }

        @Override
        public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
            if(markerListener==null) return;
            try{
                markerListener.onMarkerSelected(mapPOIItem.getUserObject());
            }
            catch(ClassCastException e){
                Log.d("CAST_ERR", "POIItemListener에서 캐스팅 에러 발생");
            }
        }
        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
            if(markerListener==null) return;
            try{
                markerListener.onMarkerBalloonSelected(mapPOIItem.getUserObject());
            }
            catch(ClassCastException e){
                Log.d("CAST_ERR", "POIItemListener에서 캐스팅 에러 발생");
            }
        }

        @Override
        @Deprecated
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

        }
        @Override
        public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

        }
    }
}


