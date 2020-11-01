package com.uos.upkodah.list.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uos.upkodah.R;

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
    private SelectionListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View mainView = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = mainView.findViewById(R.id.item_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        applyAdapter();

        return mainView;
    }

    public interface ViewHolderManager{
        /**
         * 리스트의 각 아이템이 사용할 레이아웃 ID
         * @return
         */
        public int getLayoutId();

        /**
         *
         * @param view
         * @return
         */
        public RecyclerView.ViewHolder generate(View view);

        /**
         * 뷰 홀더 안의 View를 인덱스에 따라 설정하는 방법 정의
         * @param viewHolder
         * @param index
         */
        public void setViewHolder(RecyclerView.ViewHolder viewHolder, int index);

        /**
         * 총 아이템 개수
         * @return
         */
        public int getItemCount();
    }

    public void setHolderManager(ViewHolderManager manager){
        adapter = new SelectionListAdapter(manager);
    }
    public void applyAdapter(){
        if(adapter!=null) recyclerView.setAdapter(adapter);
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
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(manager.getLayoutId(), parent,false);

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