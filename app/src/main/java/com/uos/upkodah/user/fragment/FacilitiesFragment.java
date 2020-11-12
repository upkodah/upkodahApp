package com.uos.upkodah.user.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;
import com.uos.upkodah.databinding.FragmentFacilitiesBinding;
import com.uos.upkodah.user.fragment.data.FacilitiesData;
import com.uos.upkodah.util.DisplayDensityCalculator;

import java.util.ArrayList;
import java.util.List;

/**
 * 해당 클래스는 편의시설 아이콘 목록을 표출시키는 Fragment
 */
public class FacilitiesFragment extends Fragment {
    private ArrayList<ImageButton> buttonList = new ArrayList<>();
    private int[] idArr = new int[]{11,22,33,44,55,66,77,88};

    private FacilitiesData data = new FacilitiesData();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        FragmentFacilitiesBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_facilities, container, false);
        binding.setData(data);

        return binding.getRoot();
    }
}
