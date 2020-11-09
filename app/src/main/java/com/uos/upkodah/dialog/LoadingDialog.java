package com.uos.upkodah.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;

/**
 * 해당 다이얼로그는 어떤 작업을 하는 동안 단순히 작업중인 상태를 보여주는 다이얼로그입니다.
 */
public class LoadingDialog extends DialogFragment implements Runnable{
    private Dialog currentDialog;
    private boolean cancelSwitch = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View loadingDialogLayout = inflater.inflate(R.layout.dialog_loading,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.TransparentDialog)
                .setView(loadingDialogLayout)
                .setCancelable(false);

        setCancelable(false);

        currentDialog = builder.create();
        currentDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 로딩 취소를 위한 핸들러
        handler = new Handler(Looper.getMainLooper());

        // 로딩 취소를 위한 대기 쓰레드 실행
        new Thread(this).start();

        return currentDialog;
    }

    public void cancel(){
        cancelSwitch = true;
    }

    private Handler handler;
    @Override
    public void run() {
        try {
            // 다이얼로그가 생성될 때까지 대기
            while(currentDialog==null){
                Thread.sleep(100);
            }

            // 취소 신호가 들어올 때까지 대기
            while(true){
                Thread.sleep(100);
                if(cancelSwitch){
                    // 취소 신호가 들어오면 취소시킴
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            currentDialog.cancel();
                        }
                    });
                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
