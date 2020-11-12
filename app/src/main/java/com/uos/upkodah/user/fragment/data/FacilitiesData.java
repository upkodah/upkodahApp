package com.uos.upkodah.user.fragment.data;

import android.view.View;
import android.widget.Button;

import androidx.databinding.BaseObservable;

import java.util.HashMap;

public class FacilitiesData extends BaseObservable {
    private HashMap<Integer, Status> facilitiesHash = new HashMap<>();

    public void onClickFacBtn(View btn){
        int key = btn.getId();

        if(!facilitiesHash.containsKey(key)){
            Status s = new Status();
            s.selected = false;
            facilitiesHash.put(key, s);
        }
        Status status = facilitiesHash.get(key);
        if(!status.selected){
            status.selected = true;
            btn.setSelected(true);
        }
        else{
            status.selected = false;
            btn.setSelected(false);
        }

    }

    private class Status{
        boolean selected;
    }
}
