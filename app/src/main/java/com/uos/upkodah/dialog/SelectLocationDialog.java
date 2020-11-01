package com.uos.upkodah.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.uos.upkodah.R;

public class SelectLocationDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // inflater 가져오기
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // 다이얼로그 이미지 가져오기
        View view = inflater.inflate(R.layout.dialog_select_location, null);
        builder.setView(view);

        return builder.create();
    }
}
