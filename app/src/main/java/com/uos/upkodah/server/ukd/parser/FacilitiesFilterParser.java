package com.uos.upkodah.server.ukd.parser;

import android.util.Log;

import androidx.annotation.Nullable;

import com.uos.upkodah.data.Facility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FacilitiesFilterParser extends UkdServerParser {
    protected JSONArray data;

    protected FacilitiesFilterParser(String response) throws JSONException, UkdResponseException {
        super(response);
        data = jobj.getJSONArray("data");

        for(int i=0;i<data.length();i++){
            JSONObject tmp = data.getJSONObject(i);
            facilities.add(new Facility(tmp.getString("code"), tmp.getInt("type"), tmp.getString("name"), tmp.getString("icon")));
        }
    }
    @Nullable
    public static FacilitiesFilterParser getInstance(String response){
        try {
            return new FacilitiesFilterParser(response);
        } catch (JSONException e) {
            Log.d("UKDSERVER", "파싱 오류 발생");
            e.printStackTrace();
            return null;
        } catch (UkdResponseException e) {
            Log.d("UKDSERVER", "서버 응답 오류 발생");
            return null;
        }
    }

    private final List<Facility> facilities = new ArrayList<>();
    public List<Facility> getFilters(){
        return facilities;
    }
}
