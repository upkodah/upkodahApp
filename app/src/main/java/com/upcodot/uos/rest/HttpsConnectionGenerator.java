package com.upcodot.uos.rest;

import com.upcodot.uos.rest.exception.UcdHttpRequestException;
import com.upcodot.uos.rest.exception.UcdHttpUrlException;

import javax.net.ssl.HttpsURLConnection;

@FunctionalInterface
public interface HttpsConnectionGenerator {
    public HttpsURLConnection generate();
}
