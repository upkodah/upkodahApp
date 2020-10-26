package com.uos.upkodah.server;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class UkdServerRequest extends StringRequest {
    public UkdServerRequest(int method, String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }
}
