package com.uos.upkodah.user.fragment.data;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.uos.upkodah.user.fragment.SearchBarFragment;

public class SearchBarData extends BaseObservable {
    private String searchText;

    public SearchBarData(){
        searchText = "";
    }

    @Bindable
    public String getSearchText() {
        return searchText;
    }
    public void setSearchText(String searchText) {
        notifyChange();
        this.searchText = searchText;
    }

    private SearchBarFragment.BtnListener searchBarBtnListener = null;
    public void onSearchBtnClick(View view){
        if(searchBarBtnListener != null){
            searchBarBtnListener.onClickSearchBtn(view, searchText);
        }
    }
    public void setSearchBtnListener(SearchBarFragment.BtnListener searchBtnListener) {
        this.searchBarBtnListener = searchBtnListener;
    }

}
