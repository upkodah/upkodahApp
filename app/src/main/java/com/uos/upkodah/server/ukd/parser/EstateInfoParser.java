package com.uos.upkodah.server.ukd.parser;

import android.util.Log;

import androidx.annotation.Nullable;

import com.uos.upkodah.data.local.estate.Room;
import com.uos.upkodah.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EstateInfoParser extends UkdServerParser{
    private Room resultRoom;

    protected EstateInfoParser(String response) throws JSONException, UkdResponseException {
        super(response);
        /* data라는 이름의 오브젝트를 긁어온다. */
//        for(JSONObject j : JSONUtil.getJSONObjectList(jobj, "data")){
//            resultRooms.add(new RoomInformation(j));
//        }

        for(JSONObject j : JSONUtil.getJSONObjectList(jobj, "data")){
            //Log.d("SERVER", "매물 JSON : "+j.toString());
            resultRoom = new EstateResultParser.RoomInformation(j);
        }
    }
    @Nullable
    public static EstateInfoParser getInstance(String response){
        try {
            return new EstateInfoParser(response);
        } catch (JSONException e) {
            Log.d("SERVER", "파싱 오류 발생");
            e.printStackTrace();
            return null;
        } catch (UkdResponseException e) {
            Log.d("SERVER", "서버 응답 오류 발생");
            return null;
        }
    }

    public Room getResultRoom() {
        return resultRoom;
    }
}
