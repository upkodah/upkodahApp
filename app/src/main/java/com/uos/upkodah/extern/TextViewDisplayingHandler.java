package com.uos.upkodah.extern;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class TextViewDisplayingHandler extends Handler {
    private TextView view;

    public TextViewDisplayingHandler(TextView view, String defaultText){
        this.view = view;
        view.setText(defaultText);
    }

    @Override
    public void handleMessage(Message msg){
        System.out.println("요청받음");
        view.setText(msg.getData().getString("RESULT"));
    }
}
