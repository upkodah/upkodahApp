package com.uos.upkodah.server.ukd;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uos.upkodah.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class EstateSearchRequest extends StringRequest {
    protected EstateSearchRequest(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
        Log.d("SERVER", "요청 URL :"+url);
    }
    protected static String getSearchRequestURL(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        String result = ServerInfo.SERVER_ADDR +"/v1/rooms/1?"
                +"longitude="+ JSONUtil.get(obj, "longitude", 0.0d)
                +"latitude="+JSONUtil.get(obj, "latitude", 0.0d)
                +"limit_time="+JSONUtil.get(obj, "limit_time", 0)
                +"estate_type="+JSONUtil.get(obj, "estate_type", 0)
                +"trade_type="+JSONUtil.get(obj, "trade_type", 0)
                +"facilities="+JSONUtil.get(obj, "facilities");

        return result;
    }
    protected static String getIDRequestURL(int id){
        return ServerInfo.SERVER_ADDR+"/v1/room/"+id+"/info";
    }
    public static RequestQueue requestQueue = null;

    public static EstateSearchRequest getInstanceIDRequest(int id, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener){
        return new EstateSearchRequest(getIDRequestURL(id),listener, errorListener);
    }

    @Nullable
    public static EstateSearchRequest getInstanceSearchRequest(String json, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener){
        try {
            return new EstateSearchRequest(getSearchRequestURL(json),listener, errorListener);
        } catch (JSONException e) {
            return null;
        }
    }


    public synchronized void request(Context context){
        if(requestQueue==null) requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(this);
    }
}
