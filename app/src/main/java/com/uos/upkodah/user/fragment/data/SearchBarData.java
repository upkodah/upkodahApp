package com.uos.upkodah.user.fragment.data;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class SearchBarData extends BaseObservable {
    private String searchText;


    private View.OnClickListener searchBtnListener;

    public SearchBarData(){
        searchText = "";
        searchBtnListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 내용 없는 기본 리스너
            }
        };
    }

    @Bindable
    public String getSearchText() {
        return searchText;
    }
    public void setSearchText(String searchText) {
        notifyChange();
        this.searchText = searchText;
    }

    public void onSearchBtnClick(View view){
        searchBtnListener.onClick(view);
    }
    public void setSearchBtnListener(View.OnClickListener searchBtnListener) {
        this.searchBtnListener = searchBtnListener;
    }

}
