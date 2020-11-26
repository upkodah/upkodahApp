package com.uos.upkodah.server.extern.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class KakaoAPIParser {
    protected JSONObject jobj;
    protected JSONObject meta;
    protected JSONArray documents;

    protected boolean isValid;

    protected KakaoAPIParser(String response) throws JSONException {
        // 먼저, 응답을 JSONObject로 변환, 저장한다.
        jobj = new JSONObject(response);
        System.out.println("AY"+response);

        /*
         먼저, 이 JSON 결과는 meta와 documents로 나뉜다.
         meta(obj) : 결과 문서가 있는지, 그리고 그 문서에 대한 설명을 담는다. 변수 : total_count
         documents(arr) : 결과 문서 자체.

         참고로, documents는 배열로 정의되나, 항상 크기가 1이므로 0번째만 가져온다.
         */

        /*
         meta 처리 부분
         total_count : 문서 유효 여부 0,1로 나눔
         */
        meta = jobj.getJSONObject("meta");
        isValid = meta.getInt("total_count") >= 1 ? true : false;
        System.out.println("COORD_TO_ARRAY : meta.total_count - "+isValid);


        /*
         documents 처리 부분
         */
        documents = jobj.getJSONArray("documents");
        System.out.println("COORD_TO_ARRAY : documents 획득 성공");
    }
}
