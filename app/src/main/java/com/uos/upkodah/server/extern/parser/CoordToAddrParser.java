package com.uos.upkodah.server.extern.parser;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 좌표를 주소로 변환하는 API 사용 시 넘어오는 Response를 파싱해서 저장한다.
 */
public class CoordToAddrParser extends KakaoAPIParser{
    private JSONObject roadAddr;   // 도로명주소
    private JSONObject lotNumAddr; // 지번주소

    private String addressName;
    private List<String> regions = new ArrayList<>();
    private String buildingName;

    protected CoordToAddrParser(String response) throws JSONException{
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
         address : 지번 주소 정보
         road_address : 도로명 주소 정보
         */

        roadAddr = documents.getJSONObject(0).isNull("road_address") ? null : documents.getJSONObject(0).getJSONObject("road_address");
        lotNumAddr = documents.getJSONObject(0).isNull("address") ? null : documents.getJSONObject(0).getJSONObject("address");

        if(roadAddr!=null){
            /*
            도로명주소 처리 부분
            address_name : 전체 도로명 주소
            region_[1..3]depth_name : 지역 단위 (시도, 구, 면)
            road_name : 도로명
            underground_yn : 지하여부 (Y,N)
            main_building_no : 건물 본번
            sub_building_no : 건물 부번. 없으면 ""
            building_name : 건물 이름
            zone_no : 우편번호 5자리
         */
            addressName = roadAddr.getString("address_name");
            for(int i=1;i<=3;i++){
                String valName = "region_"+i+"depth_name";
                String region = roadAddr.getString(valName);
                if(region.isEmpty()) continue;

                regions.add(region);
            }

            buildingName = roadAddr.getString("building_name");
        }
        else{
            addressName = lotNumAddr.getString("address_name");
            for(int i=1;i<=3;i++){
                String valName = "region_"+i+"depth_name";
                String region = lotNumAddr.getString(valName);
                if(region.isEmpty()) continue;

                regions.add(region);
            }

            Log.d("MAP","검색된 주소 : "+addressName);
        }

        // 현재 간헐적으로 도로명주소가 출력되지 않는 문제 발생



    }
    public static CoordToAddrParser getInstance(String reponse){
        try {
            CoordToAddrParser result = new CoordToAddrParser(reponse);

            return result.isValid ? result : null;
        } catch (JSONException e) {
            // 파싱 실패 시 null 반환
            e.printStackTrace();
            return null;
        }
    }


    public String getPostalAddress(){
        return addressName;
    }
    public List<String> getRegions(){
        return regions;
    }
}
