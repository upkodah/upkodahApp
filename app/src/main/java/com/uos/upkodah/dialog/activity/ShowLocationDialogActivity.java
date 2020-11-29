package com.uos.upkodah.dialog.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.uos.upkodah.R;
import com.uos.upkodah.SelectEstateActivity;
import com.uos.upkodah.fragment.map.GoogleMapFragment;
import com.uos.upkodah.fragment.map.data.GoogleMapData;
import com.uos.upkodah.fragment.map.data.GoogleMapDrawableObject;

import java.util.List;

public class ShowLocationDialogActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_activity_show_location);


    }

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);

        if(fragment instanceof GoogleMapFragment){
            GoogleMapFragment googleMapFragment = (GoogleMapFragment) fragment;
            GoogleMapData mapData = new GoogleMapData();
            googleMapFragment.setData(mapData);

            Intent intent = getIntent();
            List<GoogleMapDrawableObject> markers = intent.getParcelableArrayListExtra(getString(R.string.extra_markers));
            mapData.setMapMarkers(markers);
            mapData.setZoomLevelWithDepth(3);
        }
    }
}
