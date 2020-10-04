package com.uos.upkodah.user.gps;


import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uos.upkodah.rest.GPSToAddressRequest;

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
                textView.setText(response);
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
