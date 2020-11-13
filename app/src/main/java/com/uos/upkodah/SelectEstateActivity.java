package com.uos.upkodah;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.uos.upkodah.databinding.ActivitySelectEstateBinding;
import com.uos.upkodah.list.fragment.SelectionListAdapter;
import com.uos.upkodah.list.fragment.SelectionListFragment;
import com.uos.upkodah.list.fragment.data.SelectionListData;
import com.uos.upkodah.local.map.UkdMapMarker;
import com.uos.upkodah.local.map.fragment.KakaoMapFragment;
import com.uos.upkodah.local.position.CompositePositionInformation;
import com.uos.upkodah.local.position.EstateInformation;
import com.uos.upkodah.local.position.GridRegionInformation;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.local.position.RegionInformation;
import com.uos.upkodah.local.position.SubRegionInformation;
import com.uos.upkodah.local.position.temp.TempPositionGenerator;
import com.uos.upkodah.viewmodel.SelectEstateViewModel;

import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.List;


/**
 * room 정보는 요청 시 서버에서 가져온다.
 * 리스트 정보 : lon, lat, arrt, name, monPrice, DpsPrice(보증금), gridId, roomId
 */
public class SelectEstateActivity extends AppCompatActivity {
    private SelectEstateViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 뷰 모델 준비
        viewModel = new ViewModelProvider(this).get(SelectEstateViewModel.class);


        // 데이터 준비
        Intent positionDataIntent = getIntent();
        List<RegionInformation> estates = positionDataIntent.getParcelableArrayListExtra(getString(R.string.extra_region_information));
        if(estates!=null) viewModel.setEstates(estates);


        // 뷰 준비
        ActivitySelectEstateBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_select_estate);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 테스트를 위한 임시 코드
        Intent positionDataIntent = getIntent();
        PositionInformation p = positionDataIntent.getParcelableExtra(getString(R.string.extra_position_information));
        final TempPositionGenerator g = new TempPositionGenerator(this.getApplicationContext(),p.getLongitude(), p.getLatitude());

        Button b = (Button) findViewById(R.id.btn_temp);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g.checkEstates();
                g.checkSubRegions();
                g.setTotalRegion();
                g.checkRegions();
                viewModel.setEstates(g.getTotalRegion());
            }
        });
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);

        if(fragment instanceof KakaoMapFragment){
            KakaoMapFragment kakaoMapFragment = (KakaoMapFragment) fragment;
            kakaoMapFragment.setData(viewModel.mapData);

            viewModel.zoomListener = new EstateZoomListener();
            kakaoMapFragment.setZoomListener(viewModel.zoomListener);

            viewModel.markerListener = new EstateMarkerListener();
            kakaoMapFragment.setMarkerListener(viewModel.markerListener);
        }
        if(fragment instanceof SelectionListFragment){
            SelectionListFragment selectionListFragment = (SelectionListFragment) fragment;
            selectionListFragment.setData(viewModel.listData);
        }
    }


    private class EstateZoomListener implements KakaoMapFragment.ZoomListener{
        @Override
        public void onZoomChanged(MapView mapView, float zoomLevel) {
            // 줌이 변경되면, 줌에 따라 포함될 수 있는 Grid 크기를 구한다.
            int depth;

            if(zoomLevel > SelectEstateViewModel.LEVEL1){
                // 구 단위로 보여준다.
                Log.d("MAP", "REGIONMEASURE : "+RegionInformation.MEASURE+",  ZOOM : "+zoomLevel);
                depth = 1;
            }
            else if(zoomLevel > SelectEstateViewModel.LEVEL2){
                // 동 단위로 보여준다.
                Log.d("MAP", "SUBMEASURE : "+ SubRegionInformation.MEASURE+", ZOOM : "+zoomLevel);
                depth = 2;
            }
            else{
                // 그리드 단위로 보여준다
                Log.d("MAP", "GRIDMEASURE : "+ GridRegionInformation.MEASURE+", ZOOM : "+zoomLevel);
                depth = 3;
            }

            // 마커를 변경한다.
            if(viewModel.currentDepth != depth){
                viewModel.mapData.setMapMarkers(viewModel.getDisplayedEstateList(depth));
                viewModel.currentDepth = depth;
            }
        }
    }
    private class EstateMarkerListener extends UkdMapMarker.Listener{
        @Override
        public void onMarkerSelected(MapView mapView, UkdMapMarker marker, PositionInformation positionInformation) {
            Log.d("LIST", "리스트 표출 준비");
            CompositePositionInformation compositePositionInformation = (CompositePositionInformation) positionInformation;
            viewModel.setListEstateData(compositePositionInformation.getAllEstates());
        }
        @Override
        public void onMarkerBalloonSelected(MapView mapView, UkdMapMarker marker, PositionInformation positionInformation) {
            // 마커가 선택되면 마커의 타입을 먼저 확인한다.
            if(positionInformation instanceof GridRegionInformation){
            }
            else{
                //만약 선택된 마커가 Grid가 아닐 경우, 줌과 중심점을 변경시킨다.
                viewModel.mapData.setCenterLongitude(positionInformation.getLongitude());
                viewModel.mapData.setCenterLatitude(positionInformation.getLatitude());
                viewModel.currentDepth++;
                viewModel.mapData.setZoomLevel(viewModel.getZoomUsingDepth(viewModel.currentDepth));
                viewModel.zoomListener.onZoomChanged(mapView, mapView.getZoomLevelFloat());
            }
        }
    }
}
