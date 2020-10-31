package com.uos.upkodah;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.uos.upkodah.databinding.ActivitySelectEstateBinding;
import com.uos.upkodah.databinding.ActivitySelectRegionBinding;
import com.uos.upkodah.local.map.fragment.KakaoMapFragment;
import com.uos.upkodah.local.position.EstateInformation;
import com.uos.upkodah.slide.SliderFragment;

import java.util.List;

public class SelectRegionActivity extends AppCompatActivity {
    private List<EstateInformation> regions;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // 데이터 준비
        Intent positionDataIntent = getIntent();
        regions = positionDataIntent.getParcelableArrayListExtra(getString(R.string.extra_position_information));

        // 뷰 준비
        ActivitySelectRegionBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_select_estate);


    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);

        if(fragment instanceof KakaoMapFragment){
            KakaoMapFragment kakaoMapFragment = (KakaoMapFragment) fragment;
            kakaoMapFragment.setPositions(regions);
        }
    }

}
