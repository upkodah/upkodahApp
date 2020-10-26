package com.uos.upkodah.list.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.R;

import java.lang.reflect.InvocationTargetException;

/**
 * 사용자에게 선택 리스트를 보여주기 위한 프래그먼트.
 * 사용처가 추가되면 여기에 반드시 기록
 *
 * 1) 지도에서 목적지 위치 검색 시, 키워드 검색 결과 출력용
 * 2) 시간 선택 다이얼로그 출력
 * 3) 매물 거래 종류 선택 다이얼로그 출력
 * 4) 상세 매물 리스트 프래그먼트 출력
 *
 * 필요한 것 : 리스트에 들어갈 레이아웃의 id, 해당 View를 구성할 때 쓰이는 ViewHolder 생성 인터페이스
 */
public class SelectionListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.list_frag, container, false);
    }

    public interface ViewHolderManager{
        public int getId();
        public RecyclerView.ViewHolder generate(View view);
        public void setViewHolder(RecyclerView.ViewHolder viewHolder, int index);
        public int getItemCount();
    }
}

class SelectionListAdapter extends RecyclerView.Adapter{
    private SelectionListFragment.ViewHolderManager manager;

    SelectionListAdapter(SelectionListFragment.ViewHolderManager manager){
        this.manager = manager;
    }

    /**
     * 해당 메소드는 RecyclerView의 요청에 따라 삽입될 뷰를 준비하는 메소드다.
     * 여기에서 Inflater를 이용해 ViewHolder를 생성한다.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(manager.getId(), parent,false);

        RecyclerView.ViewHolder holder = manager.generate(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        manager.setViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return manager.getItemCount();
    }
}