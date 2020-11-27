package com.uos.upkodah.test;

import android.content.Context;

import com.android.volley.Response;
import com.uos.upkodah.data.local.estate.EstateInformation;
import com.uos.upkodah.data.local.estate.Room;
import com.uos.upkodah.server.ukd.EstateSearchRequest;
import com.uos.upkodah.server.ukd.FacilitiesFilterRequest;
import com.uos.upkodah.server.ukd.parser.EstateResultParser;

import java.util.ArrayList;
import java.util.List;

public class TestEstateGetter implements Response.Listener<String>{
    private List<EstateInformation> estates = new ArrayList<>();

    public TestEstateGetter(Context context){
        for(int i=1;i<=50;i++){
            new EstateSearchRequest(TestURLs.getUrl(i), this, null).request(context);
        }
    }

    @Override
    public void onResponse(String response) {
        EstateResultParser parser = EstateResultParser.getInstance(response);

        if(parser!=null){
            for(Room r : parser.getResultRooms()){
                estates.add(new EstateInformation(r));
            }
        }
    }

    public List<EstateInformation> getEstates(){
        return new ArrayList<>(estates);
    }
}
