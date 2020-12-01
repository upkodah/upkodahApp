package com.uos.upkodah.fragment.image;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ImagePagerAdapter extends FragmentStateAdapter {
    private final String[] imageUrls;

    public ImagePagerAdapter(@NonNull FragmentActivity fragmentActivity, String...urls) {
        super(fragmentActivity);
        imageUrls = new String[urls.length];

        System.arraycopy(urls, 0, imageUrls, 0, imageUrls.length);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new ImagePagerFragment(imageUrls[position]);
    }

    @Override
    public int getItemCount() {
        return imageUrls.length;
    }
}
