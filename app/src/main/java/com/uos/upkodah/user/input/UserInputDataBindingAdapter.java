package com.uos.upkodah.user.input;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class UserInputDataBindingAdapter {
    @BindingAdapter("app:setLimitTimeText")
    public static void setLimitTimeText(TextView view, int value){
        if(value<=0)
            view.setText("제한 없음");
        else
            view.setText((value)+"분");
    }

    @BindingAdapter("app:setPositionText")
    public static void setPositionText(TextView view, String value){
        if(value==null) view.setText("");
        else{
            view.setText(value);
        }
    }
}
