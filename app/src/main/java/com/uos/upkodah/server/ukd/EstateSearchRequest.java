package com.uos.upkodah.server.ukd;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.uos.upkodah.data.InputData;

public class EstateSearchRequest extends StringRequest {
    protected EstateSearchRequest(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }
    protected final static String SEARCH_ESTATE_URL = "http://34.64.166.133/v1/room/info/";


    private InputData input;


}
