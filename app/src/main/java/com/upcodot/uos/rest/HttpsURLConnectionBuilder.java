package com.upcodot.uos.rest;

import com.upcodot.uos.rest.exception.UcdHttpRequestException;
import com.upcodot.uos.rest.exception.UcdHttpUrlException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class HttpsURLConnectionBuilder {
    private HttpsURLConnection connection;

    public HttpsURLConnectionBuilder(String url, String query) throws UcdHttpRequestException, UcdHttpUrlException {
        URL connectionUrl = null;

        try {
            String utf8Query = URLEncoder.encode(query,"UTF-8");
            connectionUrl = new URL(url+"?query="+utf8Query);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            throw(new UcdHttpUrlException("URL 생성 오류"));
        }

        try {
            connection = (HttpsURLConnection) connectionUrl.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
        } catch (IOException e) {
            throw(new UcdHttpRequestException("연결 오류"));
        }
    }

    public HttpsURLConnectionBuilder addParameter(String name, String value){
        connection.setRequestProperty(name, value);

        return this;
    }

    public HttpsURLConnection build(){
        return connection;
    }
}
