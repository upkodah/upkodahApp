package com.uos.upkodah.local.map.kakao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;
import com.uos.upkodah.databinding.FragmentMapBinding;
import com.uos.upkodah.local.map.kakao.UkdMapMarker;
import com.uos.upkodah.local.map.kakao.fragment.data.KakaoMapData;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

/**
 * 카카오 지도를 띄우는 프래그먼트
 * 중심좌표, 줌 수준에 따라 적절한 지도를 표출하는 것을 주 목표로 한다.
 * 이 지도의 마커는 화살표 마커, 원 마커가 존재하며
 * 클릭하면 지정된 동작을 수행한다.
 */
public class KakaoMapFragment extends Fragment {
    private KakaoMapData data = new KakaoMapData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        FragmentMapBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        View mapViewContainer = binding.getRoot();
        binding.setData(data);

        MapView mapView = mapViewContainer.findViewById(R.id.map_kakao);
        data.setListenerInto(mapView);

        return mapViewContainer;
    }
    public void setData(KakaoMapData data) {
        this.data = data;
    }
}
