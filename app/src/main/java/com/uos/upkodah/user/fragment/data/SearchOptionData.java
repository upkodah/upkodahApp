package com.uos.upkodah.user.fragment.data;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class SearchOptionData extends BaseObservable {
    private String optionText;
    private View.OnClickListener optionEditListener;

    public SearchOptionData(){
        optionText = "";
        optionEditListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 내용 없는 기본 리스너
            }
        };
    }

    @Bindable
    public String getOptionText() {
        return optionText;
    }
    public void setOptionText(String optionText) {
        notifyChange();
        this.optionText = optionText;
    }

    public void onEditTextClick(View view){
        optionEditListener.onClick(view);
    }
    public void setOptionEditListener(View.OnClickListener optionEditListener) {
        this.optionEditListener = optionEditListener;
    }


}
