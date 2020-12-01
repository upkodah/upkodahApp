package com.uos.upkodah.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.uos.upkodah.R;
import com.uos.upkodah.data.mapping.InnerMapping;

public class SelectItemDialog extends DialogFragment {
    private InnerMapping mapping;
    private String title;
    private DialogInterface.OnClickListener listener;

    private SelectItemDialog(@NonNull String title, @NonNull InnerMapping mapping){
        this.title = title;
        this.mapping = mapping;
    }
    public SelectItemDialog setListener(DialogInterface.OnClickListener listener){
        this.listener = listener;
        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.UKDDialog);

        String[] itemList = mapping.getStringList();

        builder.setTitle(this.title)
                .setItems(itemList, this.listener);

        AlertDialog result = builder.create();
        ListView listView = result.getListView();
        listView.setDivider(new ColorDrawable(getContext().getColor(R.color.color_ukd_sky)));
        listView.setDividerHeight(2);


        return result;
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
                result = new SelectItemDialog("몇 분 안에 도착해야 하나요?", InnerMapping.LIMIT_TIME);
                return result;
            case ESTATE_TYPE:
                result = new SelectItemDialog("매물 타입 선택", InnerMapping.ESTATE);
                return result;
            case TRADE_TYPE:
            default:
                result = new SelectItemDialog("전세/월세 선택", InnerMapping.TRADE);
                return result;
        }
    }
}