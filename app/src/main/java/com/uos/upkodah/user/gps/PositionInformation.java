package com.uos.upkodah.user.gps;


import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uos.upkodah.rest.GPSToAddressRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class PositionInformation {
    private double longitude;
    private double latitude;
    private String postalAddress;

    public PositionInformation(double longitude, double latitude, String postalAddress){
        this.longitude = longitude;
        this.latitude = latitude;

        this.postalAddress = postalAddress;
    }
    public PositionInformation(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;

        this.postalAddress = "";
    }

    public void requestPostalAddress(final TextView textView){
        RequestQueue requestQueue = Volley.newRequestQueue(textView.getContext());

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 결과는 JSON이므로, 적절히 변환한다.
                try {
                    JSONObject jobj = new JSONObject(response).getJSONArray("documents").getJSONObject(0).getJSONObject("address");

                    textView.setText(jobj.get("address_name").toString());
                } catch (JSONException e) {
                    textView.setText(response);
                }
            }
        };

        StringRequest request = new GPSToAddressRequest(longitude,latitude,listener,null);

        requestQueue.add(request);
    }

    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public String getPostalAddress() {
        return postalAddress;
    }
}
