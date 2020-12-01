package com.uos.upkodah.server.ukd;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class FacilitiesFilterRequest extends StringRequest {
    public FacilitiesFilterRequest(Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(URL, listener, errorListener);
        Log.d("SERVER", "요청 URL :"+URL);
    }

    protected final static String URL = "http://34.64.236.95/v1/setting";

    public static RequestQueue requestQueue = null;
    public synchronized void request(Context context){
        if(requestQueue==null) requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(this);
    }
}
