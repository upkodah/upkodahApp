package com.uos.upkodah.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

public class SelectLimitTimeDialog extends DialogFragment{
    private String[] minuteList = new String[]{"제한 없음","10분","20분","30분","40분","50분","60분"};
    private DialogInterface.OnClickListener listener;
    private FragmentActivity activity;

    /**
     * 리스너는 0번부터 6번 원소까지 각각
     * "전체","10분","20분","30분","40분","50분","60분"
     * 에 해당하는 값 중 하나다. 즉, 0이면 무한대를 뜻한다.
     *
     * 리스너 작성 시, 별도로 마련된 'intexToResult' 정적 메소드 활용 권장
     * @param listener : 이 Dialog의 결과를 반영할 View나 기타 위치를 설정하는 리스너 장착
     */
    public SelectLimitTimeDialog(FragmentActivity activity, DialogInterface.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("몇 분 안에 도착해야 하나요?")
                .setItems(minuteList, listener);

        return builder.create();
    }

    public static int indexToResult(int i){
        return i*10;
    }
    public void show(String tag){
        this.show(activity.getSupportFragmentManager(), tag);
    }
}
