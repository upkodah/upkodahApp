package com.uos.upkodah.user.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;
import com.uos.upkodah.databinding.FragmentSearchOptionBinding;
import com.uos.upkodah.user.fragment.data.SearchOptionData;

public class SearchOptionFragment extends Fragment {
    private FragmentSearchOptionBinding binding;
    private SearchOptionData data;
    private Drawable iconImage = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_option, container, false);
        binding.setData(data);
        View view = binding.getRoot();

        EditText editText = (EditText) view.findViewById(R.id.search_option_txt);
        editText.setInputType(InputType.TYPE_NULL);
        editText.setFocusable(false);

        // 아이콘이 설정되어 있으면, 해당 아이콘으로 변경한다.
        if(iconImage != null){
            ImageView imageView = (ImageView) view.findViewById(R.id.search_option_icon);
            imageView.setImageDrawable(iconImage);
        }

        return view;
    }

    public void setData(SearchOptionData data){
        this.data = data;
    }
    public void setImage(@DrawableRes int id){
        iconImage = getResources().getDrawable(id, null);
    }
}
