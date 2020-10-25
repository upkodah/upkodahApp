package com.uos.upkodah;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.uos.upkodah.viewmodel.UkdMainViewModel;

public class UkdMainActivity extends AppCompatActivity{
    private UkdMainViewModel ukdMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ukd_main_activity);

        // 뷰모델 설정
        ukdMainViewModel = ViewModelProviders.of(this).get(UkdMainViewModel.class);

        // 다이얼로그 초기화
        ukdMainViewModel.initDialog(this);
    }


}