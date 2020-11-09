package com.uos.upkodah.local.map.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;
import com.uos.upkodah.databinding.FragmentMapBinding;
import com.uos.upkodah.local.map.UkdMapMarker;
import com.uos.upkodah.local.map.fragment.data.KakaoMapData;
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
    private Listener mapListener = new Listener();
    private UkdMapMarker.Listener markerListener;

    private KakaoMapData data = new KakaoMapData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        FragmentMapBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        View mapViewContainer = binding.getRoot();
        binding.setData(data);

        MapView mapView = mapViewContainer.findViewById(R.id.map_kakao);
        mapView.setMapViewEventListener(mapListener);
        mapView.setSelected(true);

        if(markerListener!=null) mapView.setPOIItemEventListener(markerListener);

        return mapViewContainer;
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
        markerListener = listener;
    }

    public void setData(KakaoMapData data) {
        this.data = data;
    }

    public interface ZoomListener{
        public void onZoomChanged(MapView mapView, float zoomLevel);
    }
    private class Listener implements MapView.MapViewEventListener{
        private KakaoMapFragment.ZoomListener zoomListener = null;

        @Override
        public void onMapViewInitialized(MapView mapView) {
            mapView.setZoomLevel(2,true);
        }
        @Override
        public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
            data.applyCenterData(mapPoint);
        }

        @Override
        public void onMapViewZoomLevelChanged(MapView mapView, int i) {
            if(zoomListener!=null) zoomListener.onZoomChanged(mapView, mapView.getZoomLevelFloat());
            data.
                    setMapRect(
                            mapView);
            System.out.println(data.getMapRect()+", 줌="+mapView.getZoomLevelFloat());
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
