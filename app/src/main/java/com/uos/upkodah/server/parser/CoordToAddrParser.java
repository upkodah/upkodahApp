package com.uos.upkodah.server.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 좌표를 주소로 변환하는 API 사용 시 넘어오는 Response를 파싱해서 저장한다.
 */
public class CoordToAddrParser {
    private JSONObject jobj;
    private JSONObject meta;
    private JSONObject documents;

    private boolean isValid;

    private JSONObject roadAddr;   // 도로명주소
    private JSONObject lotNumAddr; // 지번주소

    private String addressName;
    private List<String> regions = new ArrayList<>();
    private String buildingName;

    protected CoordToAddrParser(String response) throws JSONException{
        // 먼저, 응답을 JSONObject로 변환, 저장한다.
        jobj = new JSONObject(response);
        System.out.println("전체 문서 : \n"+jobj.toString());

        /*
         먼저, 이 JSON 결과는 meta와 documents로 나뉜다.
         meta(obj) : 변환된 주소가 있는지를 나타낸다. 변수 : total_count
         documents(arr) : 변환된 주소에 대한 지번 주소 정보와 도로명 주소 정보를 가지고 있다. 변수 : address, road_address

         참고로, documents는 배열로 정의되나, 항상 크기가 1이므로 0번째만 가져온다.
         */

        /*
         meta 처리 부분
         total_count : 문서 유효 여부 0,1로 나눔
         */
        meta = jobj.getJSONObject("meta");
        isValid = meta.getInt("total_count") == 1 ? true : false;
        System.out.println("COORD_TO_ARRAY : meta.total_count - "+isValid);


        /*
         documents 처리 부분
         address : 지번 주소 정보
         road_address : 도로명 주소 정보
         */
        documents = jobj.getJSONArray("documents").getJSONObject(0);
        System.out.println("COORD_TO_ARRAY : documents 획득 성공");

        roadAddr = documents.isNull("road_address") ? null : documents.getJSONObject("road_address");
        lotNumAddr = documents.isNull("address") ? null : documents.getJSONObject("address");
//        System.out.println(documents.isNull("road_address"));
//        roadAddr = documents.getJSONObject("road_address");
//        lotNumAddr = documents.getJSONObject("address");
        System.out.println("COORD_TO_ARRAY : 주소지 정보 획득 성공");

        if(roadAddr!=null){
            System.out.println("도로명주소로 검색");
            addressName = roadAddr.getString("address_name");
            for(int i=1;i<=3;i++){
                String valName = "region_"+i+"depth_name";
                String region = roadAddr.getString(valName);
                if(region.isEmpty()) continue;

                regions.add(region);
            }
            System.out.println("COORD_TO_ARRAY : 주소명 및 지역 분류 성공");

            buildingName = roadAddr.getString("building_name");

            System.out.println("검색된 주소 : "+addressName);
        }
        else{
            System.out.println("지번주소로 검색");
            addressName = lotNumAddr.getString("address_name");
            for(int i=1;i<=3;i++){
                String valName = "region_"+i+"depth_name";
                String region = lotNumAddr.getString(valName);
                if(region.isEmpty()) continue;

                regions.add(region);
            }
            System.out.println("COORD_TO_ARRAY : 주소명 및 지역 분류 성공");

//            buildingName = lotNumAddr.getString("building_name");

            System.out.println("검색된 주소 : "+addressName);
        }

        // 현재 간헐적으로 도로명주소가 출력되지 않는 문제 발생

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

    }
    public static CoordToAddrParser getInstance(String reponse){
        try {
            CoordToAddrParser result = new CoordToAddrParser(reponse);

            // 유효한 문서가 없으면 null 반환
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
