package com.uos.upkodah;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.uos.upkodah.user.input.MainUserSearchInput;
import com.uos.upkodah.user.input.SearchLocationBtnListener;
import com.uos.upkodah.user.input.UserSearchAction;
import com.uos.upkodah.user.input.test.TempSearchLocationBtnListener;
import com.uos.upkodah.user.input.test.TempUserSearchAction;

public class UkdMainActivity extends AppCompatActivity{
    private MainUserSearchInput ukdMainViewModel;
    private UserSearchAction userSearchAction = new TempUserSearchAction(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ukd_main_activity);

        // 뷰모델 설정
        ukdMainViewModel = ViewModelProviders.of(this).get(MainUserSearchInput.class);

        // TimePicker 설정
        ukdMainViewModel.setTimePicker((TimePicker) findViewById(R.id.limitTimePicker));

        // 위치정보 설정
        ukdMainViewModel.setPostalAddress((EditText) findViewById(R.id.postalAddressEdtxt));

        // 위치찾기 버튼 리스너 설정
        Button searchPosBtn = (Button) findViewById(R.id.searchPosBtn);
        SearchLocationBtnListener searchLocationBtnListener = new SearchLocationBtnListener(this);
        searchLocationBtnListener.setPositionInformation(ukdMainViewModel.getPositionInformation());
        searchPosBtn.setOnClickListener(new SearchLocationBtnListener(this));


        // 버튼리스너 설정
        Button searchEstateBtn = (Button) findViewById(R.id.searchEstateBtn);
        searchEstateBtn.setOnClickListener(ukdMainViewModel.getButtonListener(userSearchAction));
    }
}