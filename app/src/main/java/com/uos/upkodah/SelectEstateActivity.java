package com.uos.upkodah;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.uos.upkodah.databinding.ActivitySelectEstateBinding;
import com.uos.upkodah.local.map.fragment.KakaoMapFragment;
import com.uos.upkodah.local.position.EstateInformation;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.slide.SliderFragment;
import com.uos.upkodah.slide.ViewSlider;

import java.util.ArrayList;
import java.util.List;

public class SelectEstateActivity extends AppCompatActivity {
    private List<EstateInformation> estates;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // 데이터 준비
        Intent positionDataIntent = getIntent();
        estates = positionDataIntent.getParcelableArrayListExtra(getString(R.string.extra_position_information));

        // 뷰 준비
        ActivitySelectEstateBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_select_estate);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);

        if(fragment instanceof KakaoMapFragment){
            KakaoMapFragment kakaoMapFragment = (KakaoMapFragment) fragment;
            kakaoMapFragment.setPositions(estates);
        }
        if(fragment instanceof SliderFragment){

        }
    }
}
