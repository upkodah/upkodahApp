package com.uos.upkodah.server.ukd.parser;

import android.util.Log;

import androidx.annotation.Nullable;

import com.uos.upkodah.data.Facility;
import com.uos.upkodah.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FacilitiesFilterParser extends UkdServerParser {
    protected JSONArray data;

    protected FacilitiesFilterParser(String response) throws JSONException, UkdResponseException {
        super(response);

        int ERROR_INDEX = 0;

        for(JSONObject j : JSONUtil.getJSONObjectList(jobj, "data")){
            facilities.add(
                    new Facility(
                            JSONUtil.get(j, "code", "ERR_CODE_"+ERROR_INDEX),
                            JSONUtil.get(j, "type", ERROR_INDEX),
                            JSONUtil.get(j, "name", "ERR_NAME_"+ERROR_INDEX),
                            JSONUtil.get(j, "icon", "")
                    )
            );
            ERROR_INDEX++;
        }
    }
    @Nullable
    public static FacilitiesFilterParser getInstance(String response){
        try {
            return new FacilitiesFilterParser(response);
        } catch (JSONException e) {
            Log.d("SERVER", "파싱 오류 발생");
            e.printStackTrace();
            return null;
        } catch (UkdResponseException e) {
            Log.d("SERVER", "서버 응답 오류 발생");
            return null;
        }
    }

    private final List<Facility> facilities = new ArrayList<>();
    public List<Facility> getFilters(){
        return facilities;
    }
}
