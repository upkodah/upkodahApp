package com.uos.upkodah.server.extern.parser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 좌표를 주소로 변환하는 API 사용 시 넘어오는 Response를 파싱해서 저장한다.
 */
public class CoordToRegionParser extends KakaoAPIParser{
    private String[] regions = new String[4];

    protected CoordToRegionParser(String response) throws JSONException{
        // 먼저, 상위 클래스에서 meta,와 documents를 설정한다.
        super(response);

        /*
         먼저, 이 JSON 결과는 meta와 documents로 나뉜다.
         meta(obj) : 변환된 주소가 있는지를 나타낸다. 변수 : total_count
         documents(arr) : 변환된 주소에 대한 지번 주소 정보와 도로명 주소 정보를 가지고 있다. 변수 : address, road_address

         참고로, documents는 배열로 정의되나, 항상 크기가 1이므로 0번째만 가져온다.
        */

        /*
         documents 처리 부분
         */
        System.out.println("좌표 주소 변환, "+documents.toString());

        JSONObject content = documents.getJSONObject(0);
        regions[0]=content.getString("region_1depth_name");
        regions[1]=content.getString("region_2depth_name");
        regions[2]=content.getString("region_3depth_name");
        regions[3]=content.getString("region_4depth_name");


    }
    public static CoordToRegionParser getInstance(String reponse){
        try {
            CoordToRegionParser result = new CoordToRegionParser(reponse);

            // 유효한 문서가 없으면 null 반환
            System.out.println("문서 유효성 : "+result.isValid);
            return result.isValid ? result : null;
        } catch (JSONException e) {
            // 파싱 실패 시 null 반환
            System.out.println("문서가 유효하지 않습니다.");
            e.printStackTrace();
            return null;
        }
    }

    public String getRegion(int index){
        if(0<=index && index<regions.length)
            return regions[index];
        else
            return "";
    }
}
