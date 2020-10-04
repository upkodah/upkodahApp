package com.upcodot.uos.user.input;

import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.lifecycle.ViewModel;

public class MainUserSearchInput extends ViewModel implements UserSearchInput {
    private int limitTimeMin = 30;
    private String keyword = "";
    private PositionInformation position = new PositionInformation(0,0);

    public void setTimePicker(TimePicker timePicker){
        timePicker.setIs24HourView(true);
        timePicker.setMinute(limitTimeMin%60);
        timePicker.setHour(limitTimeMin/60);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                limitTimeMin = i*60;
                limitTimeMin += i1;
            }
        });
    }
    public void setPostalAddress(EditText postalAddress){
        postalAddress.setText(position.getPostalAddress());
    }
    public View.OnClickListener getButtonListener(UserSearchAction action){
        return new SearchEstateBtnListener(action);
    }

    @Override
    public PositionInformation getPositionInformation() {
        return this.position;
    }
    @Override
    public int getLimitTimeMin() {
        return this.limitTimeMin;
    }
    @Override
    public String getKeyword() {
        return this.keyword;
    }

    public void setPosition(PositionInformation position) {
        this.position = position;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    private class SearchEstateBtnListener implements View.OnClickListener{
        private UserSearchAction userSearchAction;
        private SearchEstateBtnListener(UserSearchAction action){
            this.userSearchAction = action;
        }

        @Override
        public void onClick(View view) {
            // 버튼을 클릭하면 사용자 입력을 이용한 지정 동작 수행
            userSearchAction.action(MainUserSearchInput.this);
        }
    }

}
