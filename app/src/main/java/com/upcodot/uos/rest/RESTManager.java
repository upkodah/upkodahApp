package com.upcodot.uos.rest;

import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.net.ssl.HttpsURLConnection;

public class RESTManager {
    private Handler handler; // 결과를 전송할 handler
    public void postResult(String result){

    }

    private HttpsConnectionGenerator generator;

    public RESTManager(HttpsConnectionGenerator generator){
        this.generator = generator;
    }

    public void httpGet(){
        new HttpRequestThread().start();
    }
    private class HttpRequestThread extends Thread{
        @Override
        public void run(){
            HttpsURLConnection connection = RESTManager.this.generator.generate();
            if(connection == null) return;

            try {
                StringBuilder result = new StringBuilder();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                String line;


                while((line = in.readLine()) != null) {
                    result.append(line+"\n");
                }

                RESTManager.this.postResult(result.toString());
            } catch (IOException e) {
                return;
            }
        }
    }
}
