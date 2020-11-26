package com.uos.upkodah.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class SelectItemDialog extends DialogFragment {
    private String[] itemList = new String[]{"원룸","투룸","오피스텔"};
    private String title;
    private DialogInterface.OnClickListener listener;

    private SelectItemDialog(@NonNull String title, @NonNull String...itemList){
        this.title = title;
        this.itemList = itemList;
    }
    public SelectItemDialog setListener(DialogInterface.OnClickListener listener){
        this.listener = listener;
        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(this.title)
                .setItems(this.itemList, this.listener);

        return builder.create();
    }


    public String indexToResult(int i){
        return itemList[i];
    }

    public final static int LIMIT_TIME = 0;
    public final static int ESTATE_TYPE = 1;
    public final static int TRADE_TYPE = 2;

    @IntDef(value={LIMIT_TIME, ESTATE_TYPE, TRADE_TYPE})
    @interface ID{}

    public static SelectItemDialog getInstance(@ID int id){
        SelectItemDialog result;

        switch(id){
            case LIMIT_TIME:
                result = new SelectItemDialog("몇 분 안에 도착해야 하나요?", "제한 없음","10분","20분","30분","40분","50분","60분");
                return result;
            case ESTATE_TYPE:
                result = new SelectItemDialog("매물 타입 선택", "원룸","투룸","오피스텔");
                return result;
            case TRADE_TYPE:
            default:
                result = new SelectItemDialog("전세/월세 선택", "전세","월세");
                return result;
        }
    }
}