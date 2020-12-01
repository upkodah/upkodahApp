package com.uos.upkodah.fragment.searchbar;

import android.view.View;
import android.widget.EditText;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.uos.upkodah.fragment.searchbar.SearchBarFragment;

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

    private SearchBarFragment.BtnListener editTextClickListener = null;
    public void onEditTextClick(View view){
        if(editTextClickListener != null){
            editTextClickListener.onClickSearchBtn(view, searchText);
        }
    }
    public void setEditTextClickListener(SearchBarFragment.BtnListener editTextClickListener) {
        this.editTextClickListener = editTextClickListener;
    }
    public View.OnFocusChangeListener getOnFocusChangeListener(){
        return new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                onEditTextClick(view);
            }
        };
    }

    @BindingAdapter("android:onFocusChange")
    public static void setOnFocusChange(EditText view, View.OnFocusChangeListener listener){
        view.setOnFocusChangeListener(listener);
    }
}
