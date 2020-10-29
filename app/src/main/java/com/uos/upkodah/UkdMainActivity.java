package com.uos.upkodah;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.uos.upkodah.databinding.ActivityUkdMainBinding;
import com.uos.upkodah.dialog.LoadingDialog;
import com.uos.upkodah.dialog.SelectEstateTypeDialog;
import com.uos.upkodah.dialog.SelectLimitTimeDialog;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.user.fragment.FacilitiesFragment;
import com.uos.upkodah.user.fragment.SearchBarFragment;
import com.uos.upkodah.user.fragment.SearchOptionFragment;
import com.uos.upkodah.user.input.InputData;
import com.uos.upkodah.viewmodel.UkdMainViewModel;

public class UkdMainActivity extends AppCompatActivity{
    private UkdMainViewModel ukdMainViewModel;
    private LoadingDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 뷰모델 설정
        ukdMainViewModel = new ViewModelProvider(this).get(UkdMainViewModel.class);


        // 액티비티 준비
        ActivityUkdMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_ukd_main);


        // 다이얼로그 초기화
        initDialog();


        // 리스너 설정
        ukdMainViewModel.setGetMyLocationBtnListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // GPS 요청으로 positionInformation을 새로 설정한다.
                PositionInformation positionInformation = ukdMainViewModel.getPosition();
                if(positionInformation== null){
                    // PositionInformation이 null이면 새로 만든다.
                    PositionInformation.ChangeListener listener = new PositionInformation.ChangeListener() {
                        @Override
                        public void onChange(PositionInformation position) {
                            // GPS 수신에 성공하면, 그냥 해당 객체가 가진 데이터바인딩에 신호를 보낸다.
                            ukdMainViewModel.alertPositionChange();
                        }
                    };
                    positionInformation = new PositionInformation(UkdMainActivity.this, listener);
                    ukdMainViewModel.setPosition(positionInformation);
                }
                // 갱신 요청
                positionInformation.requestGPS(view.getContext());
            }
        });


        binding.setModel(ukdMainViewModel);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);

        if(fragment instanceof SearchOptionFragment){
            String tag = fragment.getTag();
            SearchOptionFragment searchOptionFragment = (SearchOptionFragment) fragment;

            if(tag.equals(getString(R.string.fragment_limit_time))){
                ukdMainViewModel.limitTimeData.setOptionEditListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectLimitTimeDialog.show(getSupportFragmentManager(),"setLimitTime");
                    }
                });
                searchOptionFragment.setData(ukdMainViewModel.limitTimeData);
            }
            if(tag.equals(getString(R.string.fragment_estate_type))){
                ukdMainViewModel.estateData.setOptionEditListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectEstateTypeDialog.show(getSupportFragmentManager(),"setEstateType");
                    }
                });
                searchOptionFragment.setData(ukdMainViewModel.estateData);
            }
            if(tag.equals(getString(R.string.fragment_trade_type))){
                searchOptionFragment.setData(ukdMainViewModel.tradeTypeData);
            }
        }
        if(fragment instanceof SearchBarFragment){
            SearchBarFragment searchBarFragment = (SearchBarFragment) fragment;
            searchBarFragment.setEditDisable();

            ukdMainViewModel
                    .searchBarData
                    .setSearchBtnListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 입력값을 받는다.
                    InputData inputData = ukdMainViewModel.getUserInputData();

                    // 입력값으로 계산 요청하고, 로딩 다이얼로그 출력
                    // 이 다이얼로그는 계산이 끝나면 취소됨
                    LoadingDialog loadingDialog = new LoadingDialog();
                    loadingDialog.show(getSupportFragmentManager(), "loading_dialog");

                }
            });
            searchBarFragment.setData(ukdMainViewModel.searchBarData);
        }
        if(fragment instanceof FacilitiesFragment){

        }
    }

    private SelectLimitTimeDialog selectLimitTimeDialog;
    private SelectEstateTypeDialog selectEstateTypeDialog;
    public void initDialog(){
        this.selectLimitTimeDialog = new SelectLimitTimeDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int result = SelectLimitTimeDialog.indexToResult(i);
                ukdMainViewModel.setLimitTimeMin(result);

                dialogInterface.dismiss();
            }
        });

        this.selectEstateTypeDialog = new SelectEstateTypeDialog(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ukdMainViewModel.setEstateType(SelectEstateTypeDialog.indexToResult(i));

                dialogInterface.dismiss();
            }
        });
    }
}