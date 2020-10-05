package com.uos.upkodah.local;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.uos.upkodah.R;
import com.uos.upkodah.local.gps.PositionInformation;

import net.daum.mf.map.api.MapView;

public class SelectingLocationActivity extends AppCompatActivity {
    public final static String POSITIONINFO_EXTRA = "PositionInformation";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selecting_location_activity);

        // 지도에서 위치를 선택하고 확인을 누르면, PositionInformation 객체를 만들어 반환한다.

        // 먼저, 지도를 설정한다.
        MapView kakaoMapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.kakaoMapViewContainer);
        mapViewContainer.addView(kakaoMapView);

        // 전용 뷰 모델에 입력값을 넣고, 맵을 초기화한다.
        // 만약 입력값(기존 PositionInformation)이 없다면, 현재 위치로 PositionInformation을 얻어 맵을 초기화한다.
        Intent intent = getIntent();
        PositionInformation p = (PositionInformation) intent.getParcelableExtra(POSITIONINFO_EXTRA);
    }
}
