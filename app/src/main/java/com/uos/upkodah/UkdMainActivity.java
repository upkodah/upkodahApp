package com.uos.upkodah;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.uos.upkodah.databinding.UkdMainActivityBinding;
import com.uos.upkodah.viewmodel.UkdMainViewModel;

public class UkdMainActivity extends AppCompatActivity{
    private UkdMainViewModel ukdMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UkdMainActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.ukd_main_activity);

        // 뷰모델 설정
        ukdMainViewModel = ViewModelProviders.of(this).get(UkdMainViewModel.class);

        // 다이얼로그 초기화
        ukdMainViewModel.initDialog(this);

        // 리스너 초기화
        ukdMainViewModel.initListener(this);

        binding.setModel(ukdMainViewModel);
    }
}