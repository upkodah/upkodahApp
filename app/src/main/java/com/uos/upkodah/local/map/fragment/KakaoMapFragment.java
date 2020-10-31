package com.uos.upkodah.local.map.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;
import com.uos.upkodah.local.position.PositionInformation;

import net.daum.mf.map.api.MapView;

import java.util.List;

/**
 * 카카오 지도를 띄우는 프래그먼트
 * 중심좌표, 줌 수준에 따라 적절한 지도를 표출하는 것을 주 목표로 한다.
 * 이 지도의 마커는 화살표 마커, 원 마커가 존재하며
 * 클릭하면 지정된 동작을 수행한다.
 */
public class KakaoMapFragment extends Fragment {
    private MapView mapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mapView = new MapView(getActivity());
        ViewGroup mapViewContainer = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);

        mapViewContainer.addView(mapView);

        return mapViewContainer;
    }


    /**
     * 모든 마커를 없애고 초기화합니다.
     */
    public void setPositions(@Nullable List<PositionInformation> positionList){
        mapView.removeAllPOIItems();

        if(positionList != null){
            for(PositionInformation p : positionList){
                mapView.addPOIItem(p.getMarker());
            }
        }

    }

    /**
     * 위치와 마커를 추가합니다.
     */
    public void addPosition(PositionInformation positionInformation){
        mapView.addPOIItem(positionInformation.getMarker());
    }
}
