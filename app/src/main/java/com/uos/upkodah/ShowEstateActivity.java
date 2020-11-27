package com.uos.upkodah;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.uos.upkodah.data.local.estate.EstateInformation;
import com.uos.upkodah.databinding.ActivityShowEstateBinding;
import com.uos.upkodah.fragment.facilities.FacilitiesFragment;
import com.uos.upkodah.server.ukd.parser.EstateResultParser;
import com.uos.upkodah.viewmodel.ShowEstateViewModel;

public class ShowEstateActivity extends AppCompatActivity {
    private ShowEstateViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 데이터 준비
        viewModel = new ViewModelProvider(this).get(ShowEstateViewModel.class);


        // 인텐트 읽기
        Intent intent = getIntent();
//        EstateInformation estateInformation = intent.getParcelableExtra(getString(R.string.extra_estate_info));
        EstateResultParser parser = EstateResultParser.getInstance("{\"data\":{\"id\":1,\"createdAt\":\"2020-11-25T11:26:21Z\",\"updatedAt\":\"2020-11-25T11:26:21Z\",\"deletedAt\":null,\"roomId\":1,\"gridId\":7931516,\"latitude\":37.58332550064732,\"longitude\":127.03453527024723,\"estateType\":0,\"tradeType\":0,\"title\":\"제기동역 도보 10분 원룸\",\"price\":0,\"deposit\":6000,\"floorStr\":\"2층\",\"realSize\":13.22,\"roughSize\":13.22,\"facilities\":\"PS3, BK9, CE7, PM9, 세탁소\",\"imgUrls\":\"['http://d1774jszgerdmk.cloudfront.net/512/308CAF9A-4CCC-4F5D-9904-190AECC5FA99', 'http://d1774jszgerdmk.cloudfront.net/512/90857896-8FF7-4E32-9DC7-ED6B6F838CD6', 'http://d1774jszgerdmk.cloudfront.net/512/7FF90974-6F47-4946-A846-464DA181AA38', 'http://d1774jszgerdmk.cloudfront.net/512/D2036015-AEFB-44E8-9737-457C295EBED7']\",\"address\":\"서울특별시 동대문구 제기동\",\"roadAddress\":\"\",\"describe\":\"제기동역, 안암역에서 도보 10분 거리의 원룸입니다.\\n풀옵션입니다. 월세 500/40 가능\\n\",\"isAnimal\":false,\"isBalcony\":false,\"isElevator\":false,\"bathNum\":1,\"bedNum\":1,\"direct\":\"동\",\"heatType\":\"개별난방\",\"totalCost\":\"5만 원\",\"phoneNum\":\"01037874947\"},\"status\":\"success\"}");
        viewModel.setEstate(new EstateInformation(parser.getResultRooms().get(0)));

        ActivityShowEstateBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_show_estate);
        binding.setLocationData(viewModel.getLocationPanelDisplayable());
        binding.setTableData(viewModel.getRoomInfoTableDisplayable());
        binding.setTitlePanelData(viewModel.getTitlePanelDisplayable());
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
            FacilitiesFragment facilitiesFragment = (FacilitiesFragment) fragment;
            if(tag.equals(getString(R.string.fragment_facilities))){

            }
        }
    }
}
