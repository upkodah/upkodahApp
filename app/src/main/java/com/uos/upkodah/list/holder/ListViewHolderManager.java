package com.uos.upkodah.list.holder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

// 리스트 안에 들어갈 뷰를 설정하는 방법 정의의
public interface ListViewHolderManager{
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