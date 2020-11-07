package com.uos.upkodah.local.map.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;
import com.uos.upkodah.local.map.UkdMapMarker;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.util.GeoToMeterConverter;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;

/**
 * 카카오 지도를 띄우는 프래그먼트
 * 중심좌표, 줌 수준에 따라 적절한 지도를 표출하는 것을 주 목표로 한다.
 * 이 지도의 마커는 화살표 마커, 원 마커가 존재하며
 * 클릭하면 지정된 동작을 수행한다.
 */
public class KakaoMapFragment extends Fragment {
    private MapView mapView;
    private Listener mapListener = new Listener();
    private List<? extends PositionInformation> positions = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        System.out.println("hello");
        ViewGroup mapViewContainer = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);

        System.out.println("hello2");

        mapView = mapViewContainer.findViewById(R.id.map_kakao);

        mapView.setZoomLevel(2,true);
        mapView.setMapViewEventListener(mapListener);


        return mapViewContainer;
    }


    /**
     * 모든 마커를 없애고 초기화합니다.
     */
    public void setPositions(@Nullable List<? extends PositionInformation> positionList){
        positions = positionList;

        mapView.removeAllPOIItems();
        for(PositionInformation p : positions){
            p.drawInto(mapView);
        }
        setCenterUsingPositions();
    }

    public void removeAllMarker(){
        mapView.removeAllPOIItems();
    }

    /**
     * 위치와 마커를 추가합니다.
     */
    public void addPosition(PositionInformation positionInformation){
        positionInformation.drawInto(mapView);
    }

    /**
     * 중심좌표를 설정합니다.
     */
    public void setCenter(double longitude, double latitude){
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
    }
    /**
     * 중심좌표를 얻습니다.
     */
    public MapPoint getCenter(){
        return mapView.getMapCenterPoint();
    }

    /**
     * 줌 리스너를 설정합니다.
     */
    public void setZoomListener(@Nullable ZoomListener zoomListener){
        mapListener.zoomListener = zoomListener;
    }

    /**
     * 마커 리스너를 설정합니다.
     */
    public void setMarkerListener(@Nullable UkdMapMarker.Listener listener){
        mapView.setPOIItemEventListener(listener);
    }

    /**
     * 현재 맵 영역의 넓이를 m단위로 읽습니다.
     */
    public void printMapWidthMeter(){
        System.out.println(getMapRect().toString());
    }
    public MapRect getMapRect(){
        return new MapRect(mapView.getMapPointBounds());
    }

    /**
     * 마커 평균으로 중심 좌표를 설정합니다,
     */
    public void setCenterUsingPositions(){
        // 평균 위도와 경도. 기본값 설정 : 전체 지점들의 평균값으로 결정
        if(positions != null){
            double avgLongitude = 0;
            double avgLatitude = 0;
            for(PositionInformation p : positions){
                avgLongitude += p.getLongitude();
                avgLatitude += p.getLatitude();
                p.drawInto(mapView);
            }
            avgLongitude /= positions.size();
            avgLatitude /= positions.size();

            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(avgLatitude, avgLongitude), true);
        }
    }
    public interface ZoomListener{
        public void onZoomChanged(KakaoMapFragment mapFragment, float zoomLevel);
    }
    public class MapRect{
        public double width;
        public double height;

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
    private class Listener implements MapView.MapViewEventListener{
        private ZoomListener zoomListener = null;

        @Override
        public void onMapViewInitialized(MapView mapView) {

        }

        @Override
        public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
            printMapWidthMeter();
        }

        @Override
        public void onMapViewZoomLevelChanged(MapView mapView, int i) {
            if(zoomListener!=null) zoomListener.onZoomChanged(KakaoMapFragment.this, mapView.getZoomLevelFloat());
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
    }
}
