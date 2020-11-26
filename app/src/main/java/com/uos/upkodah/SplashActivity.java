package com.uos.upkodah;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.uos.upkodah.server.ukd.FacilitiesFilterRequest;
import com.uos.upkodah.server.ukd.parser.FacilitiesFilterParser;
import com.uos.upkodah.util.WaitAndStart;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    private WaitAndStart waitAndStart;
    private Intent nextActivityIntent;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_splash);


        waitAndStart = new WaitAndStart(1000){

            @Override
            public void onComplete() {
                startActivity(nextActivityIntent);
                finish();
            }
        };


    }

    @Override
    public void onResume(){
        super.onResume();

        // 다음 인텐트 준비
        nextActivityIntent = new Intent(SplashActivity.this, UkdMainActivity.class);

        // 편의시설 요청 리스너 생성
        new FacilitiesFilterRequest(new FacilitiesRequestListener(), null).request(this);

        // 대기 시작
        waitAndStart.start();
    }

    private class FacilitiesRequestListener implements Response.Listener<String>{
        @Override
        public void onResponse(String response) {
            // 요청을 받았으면 편의시설 파싱 후 준비 완료 요청
            FacilitiesFilterParser parser = FacilitiesFilterParser.getInstance(response);

            if(parser!=null){
                Log.d("FACILITY_REQUEST", "편의시설 정보 "+parser.getFilters().size()+"개");
                nextActivityIntent.putParcelableArrayListExtra(getString(R.string.extra_facilities_arr), new ArrayList<>(parser.getFilters()));
                waitAndStart.requestComplete();
            }
        }
    }
}
