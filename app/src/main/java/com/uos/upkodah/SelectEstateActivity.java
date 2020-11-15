package com.uos.upkodah;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.uos.upkodah.databinding.ActivitySelectEstateBinding;
import com.uos.upkodah.list.fragment.SelectionListFragment;
import com.uos.upkodah.local.map.google.GoogleMapFragment;
import com.uos.upkodah.local.map.kakao.UkdMapMarker;
import com.uos.upkodah.local.map.kakao.fragment.KakaoMapFragment;
import com.uos.upkodah.local.map.listener.MarkerListener;
import com.uos.upkodah.local.map.listener.ZoomListener;
import com.uos.upkodah.local.position.CompositePositionInformation;
import com.uos.upkodah.local.position.GridRegionInformation;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.local.position.RegionInformation;
import com.uos.upkodah.local.position.SubRegionInformation;
import com.uos.upkodah.local.position.temp.TempPositionGenerator;
import com.uos.upkodah.viewmodel.SelectEstateViewModel;

import net.daum.mf.map.api.MapView;

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


        // 페이저 준비
        ViewPager2 pager = findViewById(R.id.pager_estate);
        pager.setAdapter(new PagerAdapter(this));
        pager.setUserInputEnabled(false);
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

        Button b2 = (Button) findViewById(R.id.btn_switch);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPager2 pager = findViewById(R.id.pager_estate);
                if(pager.getCurrentItem() == 0) pager.setCurrentItem(1);
                else pager.setCurrentItem(0);
            }
        });
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);

//        if(fragment instanceof KakaoMapFragment){
//            KakaoMapFragment kakaoMapFragment = (KakaoMapFragment) fragment;
//            kakaoMapFragment.setData(viewModel.mapData);
//
//            viewModel.mapData.setZoomListener(new EstateZoomListener());
//
//            viewModel.mapData.setMarkerListener(new EstateMarkerListener());
//        }
        if(fragment instanceof SelectionListFragment){
            SelectionListFragment selectionListFragment = (SelectionListFragment) fragment;
            selectionListFragment.setData(viewModel.listData);
        }
        if(fragment instanceof GoogleMapFragment){
            GoogleMapFragment googleMapFragment = (GoogleMapFragment) fragment;
            googleMapFragment.setData(viewModel.mapData);

            viewModel.mapData.setZoomListener(new EstateZoomListener());

            viewModel.mapData.setMarkerListener(new EstateMarkerListener());
        }
    }


    private class EstateZoomListener implements ZoomListener {
        @Override
        public void onZoomChanged(float zoomLevel) {
            // 줌이 변경되면, 줌에 따라 포함될 수 있는 Grid 크기를 구한다.
            int depth = viewModel.mapData.getZoomDepth();

            // 마커를 변경한다.
            if(viewModel.currentDepth != depth){
                viewModel.mapData.setMapMarkers(viewModel.getDisplayedEstateList(depth));
                viewModel.currentDepth = depth;
            }
        }
    }
    private class EstateMarkerListener implements MarkerListener {
        @Override
        public void onMarkerSelected(Object data) {
            if(!(data instanceof CompositePositionInformation)) return;

            Log.d("LIST", "리스트 표출 준비");
            viewModel.setListEstateData(((CompositePositionInformation) data).getAllEstates());
        }
        @Override
        public void onMarkerBalloonSelected(Object data) {
            // 마커가 선택되면 마커의 타입을 먼저 확인한다.
            if(data instanceof GridRegionInformation){
            }
            else if(data instanceof PositionInformation){
                //만약 선택된 마커가 Grid가 아닐 경우, 줌과 중심점을 변경시킨다.
                viewModel.mapData.setCenter(((PositionInformation) data).getLongitude(), ((PositionInformation) data).getLatitude());
                viewModel.currentDepth++;
                viewModel.mapData.setZoomLevelWithDepth(viewModel.currentDepth);
            }
        }
    }

    private class PagerAdapter extends FragmentStateAdapter{
        private Fragment f1;
        private Fragment f2;

        public PagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
            f1 = new KakaoMapFragment();
            f1 = new GoogleMapFragment();
            f2 = new SelectionListFragment();
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch(position){
                case 0:
                    return f1;
                case 1:
                    return f2;
                default:
                    return f1;
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
