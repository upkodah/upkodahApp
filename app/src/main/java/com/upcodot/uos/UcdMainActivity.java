package com.upcodot.uos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.upcodot.uos.user.input.MainUserSearchInput;
import com.upcodot.uos.user.input.UserSearchAction;
import com.upcodot.uos.user.input.test.TempSearchPositionBtnListener;
import com.upcodot.uos.user.input.test.TempUserSearchAction;

public class UcdMainActivity extends AppCompatActivity{
    private MainUserSearchInput ucdMainViewModel;
    private UserSearchAction userSearchAction = new TempUserSearchAction(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ucd_main_activity);

        // 뷰모델 설정
        ucdMainViewModel = ViewModelProviders.of(this).get(MainUserSearchInput.class);

        // TimePicker 설정
        ucdMainViewModel.setTimePicker((TimePicker) findViewById(R.id.limitTimePicker));

        // 위치정보 설정
        ucdMainViewModel.setPostalAddress((EditText) findViewById(R.id.postalAddressEdtxt));

        // 위치찾기 버튼 리스너 설정
        Button searchPosBtn = (Button) findViewById(R.id.searchPosBtn);
        searchPosBtn.setOnClickListener(new TempSearchPositionBtnListener(this, ucdMainViewModel));


        // 버튼리스너 설정
        Button searchEstateBtn = (Button) findViewById(R.id.searchEstateBtn);
        searchEstateBtn.setOnClickListener(ucdMainViewModel.getButtonListener(userSearchAction));
    }
}