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
        this.searchText = searchText;
        notifyChange();
    }

    private boolean isFocused = false;
    @Bindable
    public boolean isFocused() {
        return isFocused;
    }
    public void setFocused(boolean focused) {
        isFocused = focused;
        notifyChange();
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
    private View.OnFocusChangeListener defaultListener;
    public void onEditTextClick(View view){
        if(editTextClickListener != null){
            editTextClickListener.onClickSearchBtn(view, searchText);
        }
    }
    public void setEditTextClickListener(SearchBarFragment.BtnListener editTextClickListener) {
        this.editTextClickListener = editTextClickListener;
    }
    public SearchBarFragment.BtnListener getEditTextClickListener(){
        return editTextClickListener;
    }

    @BindingAdapter("android:onFocusChange")
    public static void setOnFocusChange(final EditText editText, final SearchBarFragment.BtnListener listener){
        final View.OnFocusChangeListener base = editText.getOnFocusChangeListener();
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(listener!=null)
                    listener.onClickSearchBtn(view, editText.getText().toString());
            }
        });
    }
    @BindingAdapter("android:requestFocus")
    public static void requestFocus(EditText view, boolean focus){
        if(focus){
            view.requestFocus();
        }
    }


}
