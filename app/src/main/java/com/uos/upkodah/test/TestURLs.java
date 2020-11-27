package com.uos.upkodah.test;

public class TestURLs {
    protected final static String SEARCH_ESTATE_URL = "http://34.64.166.133/v1/room/info/";

    public static String getUrl(int i){
        return SEARCH_ESTATE_URL+i;
    }
}
