package com.uos.upkodah.server.parser;

import com.uos.upkodah.local.position.EstateInformation;
import com.uos.upkodah.local.position.PositionInformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchKeyworkParser extends KakaoAPIParser {
    public ArrayList<PositionInformation> getPositionList() {
        return positionList;
    }
    private ArrayList<PositionInformation> positionList;

    protected SearchKeyworkParser(String response) throws JSONException{
        super(response);

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
            String postalAddress = document.getString("road_address_name");

            // PositionInformation 생성 후 저장
            PositionInformation position = new PositionInformation(longitude, latitude, postalAddress, null);
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
            return null;
        }
    }

}
