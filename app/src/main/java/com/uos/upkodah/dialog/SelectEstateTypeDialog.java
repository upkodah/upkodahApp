package com.uos.upkodah.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

public class SelectEstateTypeDialog extends DialogFragment {
    private String[] minuteList = new String[]{"A","B","C"};
    private DialogInterface.OnClickListener listener;
    private FragmentActivity activity;

    /**
     * @param listener : 이 Dialog의 결과를 반영할 View나 기타 위치를 설정하는 리스너 장착
     */
    public SelectEstateTypeDialog(FragmentActivity activity, DialogInterface.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("매물 타입 선택")
                .setItems(minuteList, listener);

        return builder.create();
    }

    public void show(String tag){
        this.show(activity.getSupportFragmentManager(), tag);
    }
    public static String indexToResult(int i){
        return new String[]{"A","B","C"}[i];
    }
}
