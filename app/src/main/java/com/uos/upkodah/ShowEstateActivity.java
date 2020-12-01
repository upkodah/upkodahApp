package com.uos.upkodah;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Response;
import com.uos.upkodah.data.Facility;
import com.uos.upkodah.data.local.estate.EstateInformation;
import com.uos.upkodah.data.local.estate.FacilityInformation;
import com.uos.upkodah.data.local.estate.Room;
import com.uos.upkodah.databinding.ActivityShowEstateBinding;
import com.uos.upkodah.dialog.activity.ShowLocationDialogActivity;
import com.uos.upkodah.fragment.facilities.FacilitiesFragment;
import com.uos.upkodah.fragment.image.ImagePagerAdapter;
import com.uos.upkodah.fragment.map.GoogleMapDrawable;
import com.uos.upkodah.fragment.map.GoogleMapFragment;
import com.uos.upkodah.fragment.map.data.GoogleMapDrawableObject;
import com.uos.upkodah.server.extern.KakaoAPIRequest;
import com.uos.upkodah.server.ukd.EstateSearchRequest;
import com.uos.upkodah.server.ukd.parser.EstateInfoParser;
import com.uos.upkodah.server.ukd.parser.EstateResultParser;
import com.uos.upkodah.viewmodel.ShowEstateViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShowEstateActivity extends AppCompatActivity {
    private ShowEstateViewModel viewModel;
    private ActivityShowEstateBinding binding;
    private ViewPager2 pager;
    private FacilitiesFragment facilitiesFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 데이터 준비
        viewModel = new ViewModelProvider(this).get(ShowEstateViewModel.class);


        // 인텐트 읽기
        Intent intent = getIntent();
//        EstateInformation estateInformation = new EstateInformation((Room) intent.getParcelableExtra(getString(R.string.extra_room_info)));
//        viewModel.setEstate(estateInformation);
        int id = intent.getIntExtra(getString(R.string.extra_room_id), 0);
        Log.d("MAP", "ID : "+id);

        EstateSearchRequest.getInstanceIDRequest(
                id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        EstateInfoParser parser = EstateInfoParser.getInstance(response);

                        if(parser != null){
                            viewModel.setEstate(new EstateInformation(parser.getResultRoom()));
                            binding.setLocationData(viewModel.getLocationPanelDisplayable());
                            binding.setTableData(viewModel.getRoomInfoTableDisplayable());
                            binding.setTitlePanelData(viewModel.getTitlePanelDisplayable());
                            pager.setAdapter(new ImagePagerAdapter(ShowEstateActivity.this, viewModel.getTitlePanelDisplayable().getImageUrls()));
                            facilitiesFragment.setFacilitiesData(Facility.getGlobalList(viewModel.getLocationPanelDisplayable().getSelectedFacilities()));
                        }
                    }
                },
                null).request(this);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_estate);
        binding.setLocationData(viewModel.getLocationPanelDisplayable());
        binding.setTableData(viewModel.getRoomInfoTableDisplayable());
        binding.setTitlePanelData(viewModel.getTitlePanelDisplayable());


        // 지도 클릭 시, 지도 상세보기
        View view = findViewById(R.id.map_wrap);
        view.setOnClickListener(new MapClickListener());


        // ViewPager 설정
        pager = findViewById(R.id.pager_title_image);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        String tag = fragment.getTag();

        if(fragment instanceof FacilitiesFragment){
            if(tag.equals(getString(R.string.fragment_facilities))){
                facilitiesFragment = (FacilitiesFragment) fragment;
                facilitiesFragment.setFacilitiesData();
            }
        }
        if(fragment instanceof GoogleMapFragment){
            // 이곳에서 마커 준비를 해야합니다.
            GoogleMapFragment googleMapFragment = (GoogleMapFragment) fragment;
            googleMapFragment.setData(viewModel.mapData);
            viewModel.mapData.setControllable(false);
            viewModel.mapData.setCenterUsingPositions();
        }
    }

    private class MapClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            List<? extends GoogleMapDrawable> markers = viewModel.mapData.getMapMarkers();
            ArrayList<GoogleMapDrawableObject> sendMarkers = new ArrayList<>();

            for(GoogleMapDrawable g : markers){
                sendMarkers.add(new GoogleMapDrawableObject(g));
            }
            Intent intent = new Intent(getApplicationContext(), ShowLocationDialogActivity.class);
            intent.putParcelableArrayListExtra(getString(R.string.extra_markers), sendMarkers);
            startActivity(intent);
        }
    }
}
