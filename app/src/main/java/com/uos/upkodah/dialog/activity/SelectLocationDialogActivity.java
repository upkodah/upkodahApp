package com.uos.upkodah.dialog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.android.volley.Response;
import com.uos.upkodah.R;
import com.uos.upkodah.databinding.DialogActivitySelectLocationBinding;
import com.uos.upkodah.dialog.activity.viewmodel.SelectLocationViewModel;
import com.uos.upkodah.list.fragment.SelectionListFragment;
import com.uos.upkodah.local.map.kakao.UkdMapMarker;
import com.uos.upkodah.local.map.kakao.fragment.KakaoMapFragment;
import com.uos.upkodah.local.map.listener.MarkerListener;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.server.extern.KakaoAPIRequest;
import com.uos.upkodah.server.extern.parser.SearchKeyworkParser;
import com.uos.upkodah.user.fragment.SearchBarFragment;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class SelectLocationDialogActivity extends AppCompatActivity {
    private PositionInformation result;
    private SelectLocationViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 데이터 초기화
        Intent intent = getIntent();
        result = (PositionInformation) intent.getParcelableExtra(getString(R.string.extra_position_information));

        // 만약 positionInformation이 null이 아니라면, 새로 만든다.
        if(result == null){
            result = new PositionInformation();
        }

        // 뷰 모델 설정
        // 이 뷰 모델에서는 SearchBarFragment의 Data 클래스를 가지고 있다.
        viewModel = new ViewModelProvider(this).get(SelectLocationViewModel.class);


        // 뷰 초기화
        DialogActivitySelectLocationBinding binding = DataBindingUtil.setContentView(this, R.layout.dialog_activity_select_location);
    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        String tag = fragment.getTag();

        if(fragment instanceof SearchBarFragment){
            SearchBarFragment searchBarFragment = (SearchBarFragment) fragment;
            searchBarFragment.setData(viewModel.searchBarData);

            viewModel.searchBarData.setSearchBtnListener(new SearchLocationUsingKeywordListener());
        }
        if(fragment instanceof KakaoMapFragment){
            KakaoMapFragment kakaoMapFragment = (KakaoMapFragment) fragment;
            kakaoMapFragment.setData(viewModel.kakaoMapData);

            // 마커 리스너 장착
            viewModel.kakaoMapData.setMarkerListener(new MarkerListener() {
                @Override
                public void onMarkerSelected(Object data) {
                }
                @Override
                public void onMarkerBalloonSelected(Object data) {
                    Intent result = new Intent();
                    result.putExtra(getString(R.string.extra_position_information), (PositionInformation) data);
                    setResult(getResources().getInteger(R.integer.response_location), result);

                    finish();
                }
            });
        }
        if(fragment instanceof SelectionListFragment){
            SelectionListFragment selectionListFragment = (SelectionListFragment) fragment;
            selectionListFragment.setData(viewModel.selectionListData);
        }
    }

    private class SearchLocationUsingKeywordListener implements SearchBarFragment.BtnListener, Response.Listener<String>{
        @Override
        public void onClickSearchBtn(View view, String searchText) {
            // 검색 버튼을 누르면 넘어온 문자열로 키워드 검색을 실시하고
            // 위치정보 전체를 가져온다.
            try{
                KakaoAPIRequest kakaoAPIRequest = KakaoAPIRequest.getSearchKeywordRequest(
                        searchText,
                        viewModel.kakaoMapData.getCenterLongitude(),
                        viewModel.kakaoMapData.getCenterLatitude(),
                        this,
                        null
                );
                kakaoAPIRequest.request(getApplicationContext());
            }
            catch(NullPointerException e){
                // 아직 뷰 준비가 덜 된 것이므로 재준비 요청
                Toast.makeText(getApplicationContext(), "잠시 후 시도해보세요", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onResponse(String response) {
            // 넘어온 문자열 처리 방법 정의
            // 먼저, 결과물을 분석한다.
            SearchKeyworkParser parser = SearchKeyworkParser.getInstance(response);
            if (parser == null) {
                Toast.makeText(getApplicationContext(), "검색 결과가 없습니다.",Toast.LENGTH_SHORT).show();
                return;
            }

            // 분석 결과를 ViewModel에 넣어 결과를 보여준다.
            viewModel.setPositionInformation(parser.getPositionList());
        }
    }
}
