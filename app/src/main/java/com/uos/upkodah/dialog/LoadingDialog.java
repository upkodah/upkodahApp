package com.uos.upkodah.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;

/**
 * 해당 다이얼로그는 어떤 작업을 하는 동안 단순히 작업중인 상태를 보여주는 다이얼로그입니다.
 */
public class LoadingDialog extends DialogFragment {
    private Dialog currentDialog;


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

        return currentDialog;
    }

    public void cancel(){
        currentDialog.cancel();
    }
}
