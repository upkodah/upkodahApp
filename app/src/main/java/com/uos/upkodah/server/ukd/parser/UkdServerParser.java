package com.uos.upkodah.server.ukd.parser;

import com.uos.upkodah.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class UkdServerParser {
    protected JSONObject jobj;

    protected UkdServerParser(String response) throws JSONException, UkdResponseException{
        jobj = new JSONObject(response);
        System.out.println(response);

        // status값이 success가 아니면 오류 출력
        if(!JSONUtil.checkValue(jobj, "status", "success")){
            throw(new UkdResponseException("서버 응답 오류"));
        }
    }
}
