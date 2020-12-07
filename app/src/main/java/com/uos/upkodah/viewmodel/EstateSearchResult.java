package com.uos.upkodah.viewmodel;

public class EstateSearchResult {
    private static EstateSearchResult instance = null;
    private Object data;

    public static synchronized EstateSearchResult getInstance(){
        if(instance==null) {
            instance = new EstateSearchResult();
        }

        return instance;
    }
    public void setData(Object data){
        this.data = data;
    }
    public Object getData(){
        return this.data;
    }
}
