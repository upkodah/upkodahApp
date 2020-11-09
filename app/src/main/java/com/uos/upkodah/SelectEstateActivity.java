package com.uos.upkodah;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.uos.upkodah.databinding.ActivitySelectEstateBinding;
import com.uos.upkodah.list.fragment.SelectionListFragment;
import com.uos.upkodah.local.map.fragment.KakaoMapFragment;
import com.uos.upkodah.local.position.EstateInformation;
import com.uos.upkodah.local.position.RegionInformation;
import com.uos.upkodah.viewmodel.SelectEstateViewModel;

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
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);

        if(fragment instanceof KakaoMapFragment){
            KakaoMapFragment kakaoMapFragment = (KakaoMapFragment) fragment;
            kakaoMapFragment.setData(viewModel.mapData);
        }
        if(fragment instanceof SelectionListFragment){
            SelectionListFragment selectionListFragment = (SelectionListFragment) fragment;
            selectionListFragment.setData(viewModel.listData);
        }
    }
}
