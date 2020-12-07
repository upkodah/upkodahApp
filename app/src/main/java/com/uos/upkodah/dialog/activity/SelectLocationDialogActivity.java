package com.uos.upkodah.dialog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.uos.upkodah.fragment.list.SelectionListFragment;
import com.uos.upkodah.fragment.map.GoogleMapFragment;
import com.uos.upkodah.fragment.map.listener.MarkerListener;
import com.uos.upkodah.data.local.position.PositionInformation;
import com.uos.upkodah.server.extern.KakaoAPIRequest;
import com.uos.upkodah.server.extern.parser.SearchKeyworkParser;
import com.uos.upkodah.fragment.searchbar.SearchBarFragment;

public class SelectLocationDialogActivity extends AppCompatActivity {
    private PositionInformation result;
    private SelectLocationViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 데이터 초기화
        Intent intent = getIntent();
        result = (PositionInformation) intent.getParcelableExtra(getString(R.string.extra_position_information));
        if(result!=null) result.setSelectedMarkerInit(true);


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

            if(result!=null){
                viewModel.searchBarData.setSearchText(result.getPostalAddress());
            }


            viewModel.searchBarData.setSearchBtnListener(new SearchLocationUsingKeywordListener());

            // 포커스 초기화
            viewModel.searchBarData.setFocused(true);
        }
        if(fragment instanceof GoogleMapFragment){
            GoogleMapFragment googleMapFragment = (GoogleMapFragment) fragment;
            googleMapFragment.setData(viewModel.mapData);

            if(result!=null){
                viewModel.mapData.setMapMarker(result);
                viewModel.mapData.setCenterUsingPositions();
            }

            // 마커 리스너 장착
            viewModel.mapData.setMarkerListener(new MarkerListener() {
                @Override
                public void onMarkerSelected(Object data) {
                }
                @Override
                public void onMarkerBalloonSelected(Object data) {
                    Intent resultIntent = new Intent();

                    resultIntent.putExtra(getString(R.string.extra_position_information), (PositionInformation) data);
                    setResult(getResources().getInteger(R.integer.response_location), resultIntent);

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
                        viewModel.mapData,
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
            viewModel.mapData.setCenterUsingPositions();
        }
    }
}
