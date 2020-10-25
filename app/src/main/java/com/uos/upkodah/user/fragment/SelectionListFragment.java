package com.uos.upkodah.user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;

/**
 * 사용자에게 선택 리스트를 보여주기 위한 프래그먼트.
 * 사용처가 추가되면 여기에 반드시 기록
 *
 * 1) 지도에서 목적지 위치 검색 시, 키워드 검색 결과 출력용
 * 2) 시간 선택 다이얼로그 출력
 * 3) 매물 거래 종류 선택 다이얼로그 출력
 * 4) 상세 매물 리스트 프래그먼트 출력
 */
public class SelectionListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.list_frag, container, false);
    }
}
