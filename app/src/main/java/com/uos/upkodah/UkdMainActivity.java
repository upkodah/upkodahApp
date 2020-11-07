package com.uos.upkodah;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Response;
import com.uos.upkodah.databinding.ActivityUkdMainBinding;
import com.uos.upkodah.dialog.LoadingDialog;
import com.uos.upkodah.dialog.SelectEstateTypeDialog;
import com.uos.upkodah.dialog.SelectLimitTimeDialog;
import com.uos.upkodah.dialog.activity.SelectLocationDialogActivity;
import com.uos.upkodah.util.PermissionRequiringOnClickListener;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.server.KakaoAPIRequest;
import com.uos.upkodah.server.parser.SearchKeyworkParser;
import com.uos.upkodah.user.fragment.FacilitiesFragment;
import com.uos.upkodah.user.fragment.SearchBarFragment;
import com.uos.upkodah.user.fragment.SearchOptionFragment;
import com.uos.upkodah.user.input.InputData;
import com.uos.upkodah.viewmodel.UkdMainViewModel;

import java.util.ArrayList;

public class UkdMainActivity extends AppCompatActivity{
    private UkdMainViewModel ukdMainViewModel;

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
        ukdMainViewModel.setGetMyLocationBtnListener(new PermissionRequiringOnClickListener(
                new GetMyLocationBtnListener(),
                this,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION));

        ukdMainViewModel.setGetLocationBtnListener(new PermissionRequiringOnClickListener(
                new SearchLocationBtnListener(),
                this,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION));


        binding.setModel(ukdMainViewModel);
    }
    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        String tag = fragment.getTag();

        if(fragment instanceof SearchOptionFragment){
            SearchOptionFragment searchOptionFragment = (SearchOptionFragment) fragment;

            if(tag.equals(getString(R.string.fragment_limit_time))){
                initFragmentLimitTime(searchOptionFragment);
            }
            if(tag.equals(getString(R.string.fragment_estate_type))){
                initFragmentEstateType(searchOptionFragment);
            }
            if(tag.equals(getString(R.string.fragment_trade_type))){
                initFragmentTradeType(searchOptionFragment);
            }
        }
        if(fragment instanceof SearchBarFragment){
            SearchBarFragment searchBarFragment = (SearchBarFragment) fragment;
            if(tag.equals(getString(R.string.fragment_search_destination))){
                initFragmentSearchDestination(searchBarFragment);
            }
        }
        if(fragment instanceof FacilitiesFragment){

        }
    }


    // 장착될 Fragment 초기화 메소드
    private void initFragmentLimitTime(SearchOptionFragment searchOptionFragment){
        ukdMainViewModel.limitTimeData.setOptionEditListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectLimitTimeDialog.show(getSupportFragmentManager(),getString(R.string.dialog_select_limit_time_tag));
            }
        });
        searchOptionFragment.setData(ukdMainViewModel.limitTimeData);
        searchOptionFragment.setImage(R.drawable.limit_time);
    }
    private void initFragmentEstateType(SearchOptionFragment searchOptionFragment){
        ukdMainViewModel.estateData.setOptionEditListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectEstateTypeDialog.show(getSupportFragmentManager(),getString(R.string.dialog_select_estate_type_tag));
            }
        });
        searchOptionFragment.setData(ukdMainViewModel.estateData);
        searchOptionFragment.setImage(R.drawable.estate_type);
    }
    private void initFragmentTradeType(SearchOptionFragment searchOptionFragment){
        searchOptionFragment.setData(ukdMainViewModel.tradeTypeData);
        searchOptionFragment.setImage(R.drawable.trade_type);
    }
    private void initFragmentSearchDestination(SearchBarFragment searchBarFragment){
        searchBarFragment.setEditDisable();

        ukdMainViewModel
                .searchBarData
                .setSearchBtnListener(new EstateSearchBtnListener());
        searchBarFragment.setData(ukdMainViewModel.searchBarData);
    }


    // 표시될 다이얼로그 초기화 메소드
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


    class EstateSearchBtnListener implements SearchBarFragment.BtnListener{
        @Override
        public void onClickSearchBtn(View view, String searchText) {
            // 입력값을 받는다.
            final InputData inputData = ukdMainViewModel.getUserInputData();

            // 입력값으로 계산 요청하고, 로딩 다이얼로그 출력
            // 이 다이얼로그는 계산이 끝나면 취소됨
            final LoadingDialog loadingDialog = new LoadingDialog();
            loadingDialog.show(getSupportFragmentManager(), getString(R.string.dialog_loading_tag));

            // 요청 생성
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // 이 응답에서는 결과물을 Parsing하여 전체 매물을 만들고
                    SearchKeyworkParser parser = SearchKeyworkParser.getInstance(response);

                    // 오류가 없다면 새 액티비티를 실행한다.
                    if(parser != null){
                        ArrayList<PositionInformation> positions = parser.getPositionList();

                        Intent intent = new Intent(getApplicationContext(), SelectEstateActivity.class);
                        intent.putParcelableArrayListExtra(getString(R.string.extra_position_information), positions);

                        Toast.makeText(UkdMainActivity.this, positions.size()+"", Toast.LENGTH_SHORT).show();
                        loadingDialog.cancel();
                        startActivity(intent);
                    }
                }
            };
            KakaoAPIRequest
                    .getSearchKeywordRequest("카페", inputData.getPosition().getLongitude(), inputData.getPosition().getLatitude(), 1000, listener, null)
                    .request(getApplicationContext());
        }
    }
    class SearchLocationBtnListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            // 검색 다이얼로그를 띄운다.
            Intent intent = new Intent(getApplicationContext(), SelectLocationDialogActivity.class);
            intent.putExtra(getString(R.string.extra_position_information), ukdMainViewModel.getPosition());
            startActivityForResult(intent, 1);
        }
    }
    class GetMyLocationBtnListener implements View.OnClickListener{
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
                positionInformation = new PositionInformation();
                positionInformation.setChangeListener(listener);
                positionInformation.requestGPS(UkdMainActivity.this);
                ukdMainViewModel.setPosition(positionInformation);
            }
            // 갱신 요청
            positionInformation.requestGPS(view.getContext());
        }
    }
}

