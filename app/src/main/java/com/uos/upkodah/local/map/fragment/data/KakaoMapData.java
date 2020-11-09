package com.uos.upkodah.local.map.fragment.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.uos.upkodah.BR;
import com.uos.upkodah.local.map.UkdMapMarker;
import com.uos.upkodah.local.map.fragment.KakaoMapFragment;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.util.GeoToMeterConverter;

import net.daum.android.map.coord.MapCoord;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

public class KakaoMapData extends BaseObservable{
    private List<PositionInformation> mapMarkers = null;
    @Bindable
    public List<? extends PositionInformation> getMapMarkers() {
        return mapMarkers;
    }
    public void setMapMarkers(List<PositionInformation> mapMarkers) {
        this.mapMarkers = new ArrayList<>(mapMarkers);
        notifyPropertyChanged(BR.mapMarkers);
        setCenterUsingPositions();
    }
    /**
     * 위치와 마커를 추가합니다.
     */
    public void addMapMarker(PositionInformation p){
        mapMarkers.add(p);
        notifyPropertyChanged(BR.mapMarkers);
    }
    /**
     * 모든 마커를 없애고 초기화합니다.
     */
    public void removeAllMarker(){
        this.mapMarkers = new ArrayList<>();
        notifyPropertyChanged(BR.mapMarkers);
    }

    private float zoomLevel;
    @Bindable
    public float getZoomLevel() {
        return zoomLevel;
    }

    public void setZoomLevel(float zoomLevel) {
        this.zoomLevel = zoomLevel;
        notifyPropertyChanged(BR.zoomLevel);
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

    public void applyCenterData(MapPoint mapPoint){
        centerLongitude = mapPoint.getMapPointGeoCoord().longitude;
        centerLatitude = mapPoint.getMapPointGeoCoord().latitude;
    }

    /**
     * 중심좌표를 얻습니다.
     */
    public MapPoint getCenter(){
        return MapPoint.mapPointWithGeoCoord(centerLongitude, centerLatitude);
    }

    /**
     * 마커 평균으로 중심 좌표를 설정합니다,
     */
    public void setCenterUsingPositions(){
        // 평균 위도와 경도. 기본값 설정 : 전체 지점들의 평균값으로 결정
        if(mapMarkers != null){
            double avgLongitude = 0;
            double avgLatitude = 0;
            for(PositionInformation p : mapMarkers){
                avgLongitude += p.getLongitude();
                avgLatitude += p.getLatitude();
            }
            avgLongitude /= mapMarkers.size();
            avgLatitude /= mapMarkers.size();

            setCenterLongitude(avgLongitude);
            setCenterLatitude(avgLatitude);
        }
    }

    private MapPointBounds mapBounds;
    public MapRect getMapRect(){
        return new MapRect(mapBounds);
    }
    public void setMapRect(MapView view){
        mapBounds = view.getMapPointBounds();
    }
    /**
     * 현재 맵 영역의 넓이를 m단위로 읽습니다.
     */
    public void printMapWidthMeter(){
        System.out.println(getMapRect().toString());
    }


    public class MapRect{
        public final double width;
        public final double height;

        public MapRect(MapPointBounds points){
            MapPoint botlef = points.bottomLeft;
            MapPoint topRig = points.topRight;

            MapPoint.GeoCoordinate botlefCoord = botlef.getMapPointGeoCoord();
            MapPoint.GeoCoordinate topRigCoord = topRig.getMapPointGeoCoord();

            width = GeoToMeterConverter.gpsToMeter(
                    botlefCoord.longitude,topRigCoord.latitude,
                    topRigCoord.longitude, topRigCoord.latitude);
            height = GeoToMeterConverter.gpsToMeter(
                    botlefCoord.longitude, botlefCoord.latitude,
                    botlefCoord.longitude, topRigCoord.latitude);
        }

        @NonNull
        @Override
        public String toString() {
            return "맵 크기 : 가로 "+width+"m, 세로 "+height+"m";
        }
    }
}
