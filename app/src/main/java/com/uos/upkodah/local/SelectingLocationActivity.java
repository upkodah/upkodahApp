package com.uos.upkodah.local;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.uos.upkodah.R;
import com.uos.upkodah.local.gps.PositionInformation;

import net.daum.android.map.coord.MapCoord;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class SelectingLocationActivity extends AppCompatActivity {
    private PositionInformation positionInformation;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selecting_location_activity);

        // 지도에서 위치를 선택하고 확인을 누르면, PositionInformation 객체를 만들어 반환한다.

        // 먼저, 지도를 설정한다.
        final MapView kakaoMapView = new MapView(this);
        kakaoMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);
        kakaoMapView.setZoomLevel(3, true);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.kakaoMapViewContainer);
        mapViewContainer.addView(kakaoMapView);

        // 전용 뷰 모델에 입력값을 넣고, 맵을 초기화한다.
        // 만약 입력값(기존 PositionInformation)이 없다면, 현재 위치로 PositionInformation을 얻어 맵을 초기화한다.
        Intent intent = getIntent();
        positionInformation = (PositionInformation) intent.getParcelableExtra(PositionInformation.EXTRA);
        if(positionInformation==null){
            positionInformation = new PositionInformation(0,0,"");
            positionInformation.requestGPS(this, new PositionInformation.Listener() {
                @Override
                public void onResponse() {
                    kakaoMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(positionInformation.getLatitude(), positionInformation.getLongitude()), true);
                }
            });
        }
        else{
            kakaoMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(positionInformation.getLatitude(), positionInformation.getLongitude()), true);
        }

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("Default Marker");
        marker.setTag(1);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(positionInformation.getLatitude(), positionInformation.getLongitude()));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        kakaoMapView.addPOIItem(marker);

    }
}
