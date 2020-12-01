package com.uos.upkodah;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.uos.upkodah.data.Facility;
import com.uos.upkodah.data.local.estate.Room;
import com.uos.upkodah.data.mapping.InnerMapping;
import com.uos.upkodah.databinding.ActivityUkdMainBinding;
import com.uos.upkodah.dialog.LoadingDialog;
import com.uos.upkodah.dialog.SelectItemDialog;
import com.uos.upkodah.dialog.activity.SelectLocationDialogActivity;
import com.uos.upkodah.data.local.position.PositionInformation;
import com.uos.upkodah.data.local.position.UserPositionInformation;
import com.uos.upkodah.server.ukd.EstateSearchRequest;
import com.uos.upkodah.server.ukd.UserDataToTransmit;
import com.uos.upkodah.fragment.facilities.FacilitiesFragment;
import com.uos.upkodah.fragment.searchbar.SearchBarFragment;
import com.uos.upkodah.fragment.optionbar.SearchOptionFragment;
import com.uos.upkodah.server.ukd.parser.EstateResultParser;
import com.uos.upkodah.util.PermissionRequiringOnClickListener;
import com.uos.upkodah.viewmodel.UkdMainViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UkdMainActivity extends AppCompatActivity{
    private UkdMainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 뷰모델 설정
        viewModel = new ViewModelProvider(this).get(UkdMainViewModel.class);


        // 액티비티 준비
        ActivityUkdMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_ukd_main);


        // 다이얼로그 초기화
        initDialog();


        // 리스너 설정
        viewModel.setGetMyLocationBtnListener(new PermissionRequiringOnClickListener(
                new GetMyLocationBtnListener(),
                this,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION));

        viewModel.setGetLocationBtnListener(new PermissionRequiringOnClickListener(
                new SearchLocationBtnListener(),
                this,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION));


        binding.setModel(viewModel);
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
            if(tag.equals(getString(R.string.fragment_facilities))){
                FacilitiesFragment facilitiesFragment = (FacilitiesFragment) fragment;
                facilitiesFragment.setEditable(true);

                // 인텐트에서 편의시설 목록 가져옴
                facilitiesFragment.setFacilitiesData(Facility.getGlobalList());
                viewModel.setFacilitiesFragment(facilitiesFragment);
            }
        }
    }



    // 결과 수신 처리
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == getResources().getInteger(R.integer.request_location)){
            System.out.println("결과반환성공");
            if(resultCode == getResources().getInteger(R.integer.response_location)){
                // 위치 검색 결과 반환

                PositionInformation resultPosition = data.getParcelableExtra(getString(R.string.extra_position_information));
                System.out.println(resultPosition.getPostalAddress());
                viewModel.setPosition(resultPosition);
            }
        }
    }


    // 장착될 Fragment 초기화 메소드
    private void initFragmentLimitTime(SearchOptionFragment searchOptionFragment){
        viewModel.limitTimeData.setOptionEditListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMap.get(getString(R.string.dialog_select_limit_time_tag)).show(getSupportFragmentManager(),getString(R.string.dialog_select_limit_time_tag));
            }
        });
        searchOptionFragment.setData(viewModel.limitTimeData);
        searchOptionFragment.setImage(R.drawable.icon_clock);
    }
    private void initFragmentEstateType(SearchOptionFragment searchOptionFragment){
        viewModel.estateData.setOptionEditListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMap.get(getString(R.string.dialog_select_estate_type_tag)).show(getSupportFragmentManager(),getString(R.string.dialog_select_estate_type_tag));
            }
        });
        searchOptionFragment.setData(viewModel.estateData);
        searchOptionFragment.setImage(R.drawable.icon_building);
    }
    private void initFragmentTradeType(SearchOptionFragment searchOptionFragment){
        viewModel.tradeTypeData.setOptionEditListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogMap.get(getString(R.string.dialog_select_trade_type_tag))
                        .show(getSupportFragmentManager(),getString(R.string.dialog_select_trade_type_tag));
            }
        });
        searchOptionFragment.setData(viewModel.tradeTypeData);
        searchOptionFragment.setImage(R.drawable.icon_type);
    }
    private void initFragmentSearchDestination(SearchBarFragment searchBarFragment){
        searchBarFragment.setEditDisable();

        viewModel.searchBarData.setSearchBtnListener(new EstateSearchBtnListener());
        viewModel.searchBarData.setEditTextClickListener(new SearchBarFragment.BtnListener() {
            @Override
            public void onClickSearchBtn(View view, String searchText) {
                new SearchLocationBtnListener().onClick(view);
            }
        });
        searchBarFragment.setData(viewModel.searchBarData);
    }


    // 표시될 다이얼로그 초기화 메소드
    private final Map<String, DialogFragment> dialogMap = new HashMap<>();
    public void initDialog(){
        final SelectItemDialog timeDialog = SelectItemDialog.getInstance(SelectItemDialog.LIMIT_TIME);
        final SelectItemDialog estateDialog = SelectItemDialog.getInstance(SelectItemDialog.ESTATE_TYPE);;
        final SelectItemDialog tradeDialog = SelectItemDialog.getInstance(SelectItemDialog.TRADE_TYPE);;

        // 제한시간 설정 다이얼로그
        timeDialog.setListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int result = InnerMapping.LIMIT_TIME.getIndexList()[i];
                viewModel.setLimitTimeMin(result);

                dialogInterface.dismiss();
            }
        });
        dialogMap.put(getString(R.string.dialog_select_limit_time_tag),timeDialog);


        // 매물 타입 선택 다이얼로그
        estateDialog.setListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int result = InnerMapping.ESTATE.getIndexList()[i];
                viewModel.setEstateType(result);

                dialogInterface.dismiss();
            }
        });
        dialogMap.put(getString(R.string.dialog_select_estate_type_tag),estateDialog);


        // 거래 타입 선택 다이얼로그
        tradeDialog.setListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int result = InnerMapping.TRADE.getIndexList()[i];
                viewModel.setTradeType(result);

                dialogInterface.dismiss();
            }
        });
        dialogMap.put(getString(R.string.dialog_select_trade_type_tag),tradeDialog);
    }


    private class EstateSearchBtnListener implements SearchBarFragment.BtnListener{
        @Override
        public void onClickSearchBtn(View view, String searchText) {
            // 입력값을 받아 JSON으로 변환한다.
            final UserDataToTransmit data = new UserDataToTransmit(viewModel);
            System.out.println(data.toJSON());

            // 입력값으로 계산 요청하고, 로딩 다이얼로그 출력
            // 이 다이얼로그는 계산이 끝나면 취소됨
            final LoadingDialog loadingDialog = new LoadingDialog();
            loadingDialog.show(getSupportFragmentManager(), getString(R.string.dialog_loading_tag));

            EstateSearchRequest.getInstanceSearchRequest(
                    data.toJSON(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("SERVER", "매물 요청 결과"+response);
                            EstateResultParser parser = EstateResultParser.getInstance(response);
                            Log.d("SERVER", "매물 파싱 완료 (성공여부 : "+(parser!=null)+")");

                            if (parser != null) {
                                if(parser.getResultRooms().size()>0){
                                    Intent intent = new Intent(getApplicationContext(), SelectEstateActivity.class);
                                    System.out.println("응답"+parser.getResultRooms().size());
                                    intent.putParcelableArrayListExtra(getString(R.string.extra_room_info), (ArrayList<Room>) parser.getResultRooms());

                                    loadingDialog.cancel();

                                    startActivity(intent);
                                }
                                else{
                                    loadingDialog.cancel();
                                    Toast.makeText(getApplicationContext(), "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                loadingDialog.cancel();
                                Toast.makeText(getApplicationContext(), "서버 상태가 불안정합니다.\n잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loadingDialog.cancel();
                            Log.d("SERVER", "에러 : "+error);
                            Toast.makeText(getApplicationContext(), "인터넷 연결을 확인하세요.", Toast.LENGTH_SHORT).show();
                        }
                    }
            ).request(getApplicationContext());
        }
    }
    private class SearchLocationBtnListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            // 검색 다이얼로그를 띄운다.
            Intent intent = new Intent(getApplicationContext(), SelectLocationDialogActivity.class);
            intent.putExtra(getString(R.string.extra_position_information), viewModel.getPosition());
            startActivityForResult(intent, getResources().getInteger(R.integer.request_location));
        }
    }
    private class GetMyLocationBtnListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            // GPS 요청으로 positionInformation을 새로 설정한다.
            UserPositionInformation posInfo = (UserPositionInformation) viewModel.getPosition();

            // 갱신 요청
            posInfo.requestGPS(view.getContext());
        }
    }
}

