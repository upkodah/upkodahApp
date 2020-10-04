package com.uos.upkodah.extern;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public abstract class RequestResponseModel {
    private Handler handler;

    public RequestResponseModel(Handler handler){
        this.handler = handler;
    }
    public void postResult(Object result){
        Message m = new Message();
        m.setData(toBundle(result));

        handler.sendMessage(m);
    }
    protected abstract Bundle toBundle(Object obj);
    protected abstract Object getResult();

    public void request(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                postResult(getResult());
            }
        }).start();
    }

}
