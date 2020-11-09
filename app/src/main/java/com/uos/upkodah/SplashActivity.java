package com.uos.upkodah;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.uos.upkodah.util.WaitAndStart;

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void onResume(){
        super.onResume();

        new WaitAndStart(1000){

            @Override
            public void onComplete() {
                Intent intent = new Intent(SplashActivity.this, UkdMainActivity.class);
                startActivity(intent);
                finish();
            }
        }.startTimeCheckingOnly();
    }
}
