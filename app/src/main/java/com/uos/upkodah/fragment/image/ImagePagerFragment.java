package com.uos.upkodah.fragment.image;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.uos.upkodah.R;

public class ImagePagerFragment extends Fragment {
    private final String url;
    public ImagePagerFragment(String url){
        super();
        this.url = url;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_image, container, false);
        ImageView imageView = view.findViewById(R.id.pager_image_view);
        Glide.with(this).load(url).into(imageView);

        return view;
    }
}
