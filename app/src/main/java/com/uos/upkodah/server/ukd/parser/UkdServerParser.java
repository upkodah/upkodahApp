package com.uos.upkodah.server.ukd.parser;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class UkdServerParser {
    protected JSONObject jobj;

    protected UkdServerParser(String response) throws JSONException, UkdResponseException{
        jobj = new JSONObject(response);
        System.out.println(response);

        // status값이 success가 아니면 오류 출력
        if(!jobj.getString("status").equals("success")){
            throw(new UkdResponseException("서버 응답 오류"));
        }
    }
}
