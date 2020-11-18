package com.uos.upkodah.list.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.uos.upkodah.R;
import com.uos.upkodah.databinding.FragmentListBinding;
import com.uos.upkodah.list.fragment.data.SelectionListData;

/**
 * 사용자에게 선택 리스트를 보여주기 위한 프래그먼트.
 * 사용처가 추가되면 여기에 반드시 기록
 *
 * 1) 지도에서 목적지 위치 검색 시, 키워드 검색 결과 출력용
 * 2) 상세 매물 리스트 프래그먼트 출력
 *
 * 필요한 것 : 리스트에 들어갈 레이아웃의 id, 해당 View를 구성할 때 쓰이는 ViewHolder 생성 인터페이스
 */
public class SelectionListFragment extends Fragment {
    private FragmentListBinding binding;
    private RecyclerView recyclerView;
    private SelectionListData data = new SelectionListData();
    private RecyclerView.LayoutManager layoutManager = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        binding.setData(data);

        View mainView = binding.getRoot();
        recyclerView = mainView.findViewById(R.id.list_item);
        if(layoutManager==null) layoutManager = new LinearLayoutManager(this.getContext());

        recyclerView.setLayoutManager(layoutManager);

        return mainView;
    }

    public void setData(SelectionListData data){
        this.data = data;
    }
    public void setLayoutManager(RecyclerView.LayoutManager manager){
        this.layoutManager = manager;
    }
}

