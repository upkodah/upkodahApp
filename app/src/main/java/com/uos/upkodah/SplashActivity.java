package com.uos.upkodah;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.AppLaunchChecker;

import com.uos.upkodah.splashUtil.WaitAndStart;

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.splash_activity);
    }

    @Override
    public void onResume(){
        super.onResume();

        new WaitAndStart(1000){

            @Override
            public void onComplete() {
                Intent intent = new Intent(SplashActivity.this, UkdMainActivity.class);
                startActivity(intent);
            }
        }.startTimeCheckingOnly();
    }
}
