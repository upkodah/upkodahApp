package com.uos.upkodah.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.uos.upkodah.R;

public class SelectLocationDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // inflater 가져오기
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // 기존에 생성된 뷰 확인하기
        holder = new ViewModelProvider(requireActivity()).get(ViewHolder.class);
        if(holder.view == null){
            holder.view = inflater.inflate(R.layout.dialog_select_location, null);
        }
        builder.setView(holder.view);

        return builder.create();
    }

    private ViewHolder holder;

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        super.show(manager.beginTransaction(), tag);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // 파괴될 때, 생성된 View 정보도 함께 파괴한다.
        ViewGroup parent = (ViewGroup) holder.view.getParent();
        parent.removeView(holder.view);
    }

    // 해당 클래스는 Dialog가 실행될 때, 이미 생성된 View를 재활용하기 위한 클래스
    public static class ViewHolder extends ViewModel{
        private View view = null;
    }
}
