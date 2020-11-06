package com.uos.upkodah.util;

import android.os.Handler;
import android.os.Looper;

import com.android.volley.Response;

/**
 * 해당 클래스는 일정 시간동안 대기하다가 일정 시간이 지나거나
 * 조건을 만족하면 액티비티를 실행시키는 클래스입니다.
 */
public abstract class WaitAndStart extends Thread{
    private long time;
    private Response.Listener<String> listener;

    public WaitAndStart(long time){
        this.time = time;
        listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // response가 정상인지 확인

                // 정상이라면 요청 정상화
                requestCompleted = true;
            }
        };
    }
    public void startTimeCheckingOnly(){
        requestCompleted = true;
        this.start();
    }

    // 여기에 액티비티 실행 코드 삽입
    public abstract void onComplete();

    private boolean requestCompleted = false;
    @Override
    public void run(){
        // 요청이 필요할 때(false일 때)
        if(!requestCompleted){
            // 지정 서버 통신 요청

        }

        // 대기
        try {
            Thread.sleep(time);

            // 완료 후 요청 완료 시까지 추가 대기
            while(!requestCompleted){
                System.out.println("대기중");
                Thread.sleep(1);
            }

            // 대기가 끝나면 핸들러에서 액티비티 실행
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    onComplete();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
