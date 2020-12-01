package com.uos.upkodah.server.ukd;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uos.upkodah.data.InputData;

import java.util.StringTokenizer;

public class EstateSearchRequest extends StringRequest {
    protected EstateSearchRequest(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        Log.d("SERVER", "요청 URL :"+url);
    }
    protected final static String URL = "http://34.64.166.133/v1/rooms/1";
    protected static String getRequestURL(String json){
//        return URL+"?query="+json;
//        StringTokenizer tokenizer = new StringTokenizer(json, "{,}");
//        String result = URL+"?";
        return URL;
    }

    private InputData input;
    public static RequestQueue requestQueue = null;

    public static EstateSearchRequest getInstanceIDRequest(int id, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener){
        return new EstateSearchRequest("http://34.64.236.95/v1/room/"+id+"/info",listener, errorListener);
    }
    public static EstateSearchRequest getInstanceSearchRequest(String json, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener){
        return new EstateSearchRequest("http://34.64.236.95/v1/rooms/1",listener, errorListener);
    }


    public synchronized void request(Context context){
        if(requestQueue==null) requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(this);
    }
}
