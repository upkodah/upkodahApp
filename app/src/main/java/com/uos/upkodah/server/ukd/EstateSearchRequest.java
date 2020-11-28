package com.uos.upkodah.server.ukd;

import android.content.Context;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uos.upkodah.data.InputData;

public class EstateSearchRequest extends StringRequest {
    protected EstateSearchRequest(Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(URL, listener, errorListener);
    }
    public EstateSearchRequest(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener){
        super(url, listener, errorListener);
    }
    protected final static String URL = "http://34.64.166.133/v1/room/info/";
    protected static String getRequestURL(String json){
        return URL+"?query="+json;
    }

    private InputData input;
    public static RequestQueue requestQueue = null;



    public synchronized void request(Context context){
        if(requestQueue==null) requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(this);
    }
}
