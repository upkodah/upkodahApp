package com.uos.upkodah.rest;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GPSToAddressRequest extends StringRequest {
    public GPSToAddressRequest(double longitude, double latitude, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.GET, getRequestURL(longitude,latitude), listener, errorListener);
    }
    private static String getRequestURL(double longitude, double latitude){
        String requestURL = "https://dapi.kakao.com/v2/local/geo/coord2address.json?input_corrd=WGS84"
                +"&"+"x="+longitude+"&"+"y="+latitude;
        return requestURL;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError{
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization","KakaoAK a92b21b75ce1b2ad1b8ded4dcfdc1f41");

        return headers;
    }
}
