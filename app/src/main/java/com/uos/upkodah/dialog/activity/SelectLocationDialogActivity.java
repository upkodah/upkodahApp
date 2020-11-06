package com.uos.upkodah.dialog.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.uos.upkodah.R;
import com.uos.upkodah.databinding.DialogActivitySelectLocationBinding;
import com.uos.upkodah.local.map.fragment.KakaoMapFragment;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.user.fragment.SearchBarFragment;

import java.util.ArrayList;

public class SelectLocationDialogActivity extends AppCompatActivity {
    private PositionInformation result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 데이터 초기화
        Intent intent = getIntent();
        result = (PositionInformation) intent.getParcelableExtra(getString(R.string.extra_position_information));

        // 만약 positionInformation이 null이 아니라면, 새로 만든다.
        if(result == null){
            result = new PositionInformation();
        }

        // 뷰 초기화
        DialogActivitySelectLocationBinding binding = DataBindingUtil.setContentView(this, R.layout.dialog_activity_select_location);

    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        String tag = fragment.getTag();

        if(fragment instanceof SearchBarFragment){

        }
        if(fragment instanceof KakaoMapFragment){

        }
    }
    //    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        super.onCreateDialog(savedInstanceState);
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//
//        System.out.println("카카오맵 준비");
//
//
//        // inflater 가져오기
//        LayoutInflater inflater = requireActivity().getLayoutInflater();
//        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.dialog_activity_select_location, null);
//
//        builder.setView(view);
//
//        return builder.create();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        // FragmentManager에서 기존 Fragment 삭제
//        FragmentManager manager = requireActivity().getSupportFragmentManager();
//
//        // 먼저, 해당 다이얼로그를 찾는다.
//        Fragment curFrag = manager.findFragmentByTag(getString(R.string.dialog_select_location_tag));
//
//        for(Fragment f : manager.getFragments()){
//            if(f.getParentFragment() != null)
//                System.out.println(f.getParentFragment().getTag());
//        }
//
//        ArrayList<Fragment> fragmentList = new ArrayList<>();
//
//        Fragment tmp;
//
//        FragmentManager childManager;
//
//        tmp = manager.findFragmentByTag( "location_search_window");
//        if(tmp!=null)
//            fragmentList.add(tmp);
//
//        tmp = manager.findFragmentByTag( "location_search_map");
//        if(tmp!=null)
//            fragmentList.add(tmp);
//
//
//        FragmentTransaction transaction = manager.beginTransaction();
//        for(Fragment f : fragmentList){
//            transaction.remove(f);
//        }
//        transaction.commit();
//    }
}
