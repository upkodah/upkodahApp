package com.uos.upkodah.server.extern.parser;

import android.util.Log;

import com.uos.upkodah.data.local.position.PositionInformation;
import com.uos.upkodah.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchKeyworkParser extends KakaoAPIParser {
    public ArrayList<PositionInformation> getPositionList() {
        return positionList;
    }
    private ArrayList<PositionInformation> positionList;

    protected SearchKeyworkParser(String response) throws JSONException{
        super(response);

        Log.d("SERVER", "키워드 검색 응답 : "+response);

        /*
        documents Object에서 Object를 하나씩 가져온다.
         */
        positionList = new ArrayList<>();
        for(int i=0;i<documents.length();i++){
            JSONObject document = documents.getJSONObject(i);

            // x좌표와 y좌표 획득
            double longitude = document.getDouble("x");
            double latitude = document.getDouble("y");

            // 주소 획득
            String postalAddress = JSONUtil.get(document, "road_address_name", "");

            // 이름 획득
            String name = document.getString("place_name");

            // 주소 수정
            if(postalAddress.equals("")) {
                Log.d("SERVER", "주소가 없어 지번주소로 설정 : "+JSONUtil.get(document, "address_name", ""));
                postalAddress =  JSONUtil.get(document, "address_name", "")+" "+name;
            }

            // PositionInformation 생성 후 저장
            PositionInformation position = new PositionInformation(longitude, latitude, postalAddress);
            position.setName(name);
            positionList.add(position);
        }
    }
    public static SearchKeyworkParser getInstance(String response){
        try {
            SearchKeyworkParser result = new SearchKeyworkParser(response);

            // 유효한 문서가 없으면 null 반환
            return result.isValid ? result : null;
        } catch (JSONException e) {
            // 파싱 실패 시 null 반환
            e.printStackTrace();
            Log.d("API ERROR", response);
            return null;
        }
    }

}
