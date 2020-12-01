package com.uos.upkodah.data.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InnerMapping {
    private Map<Integer, String> map = new HashMap<>();
    private List<Integer> indexList = new ArrayList<>();

    public InnerMapping(Data...mappingData){
        for(Data d : mappingData){
            indexList.add(d.index);
            map.put(d.index, d.str);
        }
    }
    public String getString(int index){
        return map.get(index);
    }
    public int[] getIndexList(){
        int[] result = new int[indexList.size()];
        int i = 0;

        for(int k : indexList){
            result[i++]=k;
        }

        return result;
    }
    public String[] getStringList(){
        String[] result = new String[indexList.size()];
        int i = 0;

        for(int key : getIndexList()){
            result[i++]=map.get(key);
        }

        return result;
    }

    public static class Data{
        private final String str;
        private final int index;

        public Data(int index, String str){
            this.str = str;
            this.index = index;
        }
    }

    public final static int ONE_ROOM = 0;
    public final static int TWO_ROOM = 1;
    public final static int OFFICETEL = 2;
    public final static int APART = 3;
    public final static int PUBLIC_HOUSING = 4;
    public final static InnerMapping ESTATE = new InnerMapping(
            new Data(ONE_ROOM, "원룸"),
            new Data(TWO_ROOM, "투룸"),
            new Data(OFFICETEL, "오피스텔"),
            new Data(APART, "아파트"),
            new Data(PUBLIC_HOUSING, "공공임대주택")
    );

    public final static int MONTHLY_RENTAL = 0;
    public final static int CHARTER_RENTAL = 1;
    public final static int SALE_HOUSE = 2;
    public final static InnerMapping TRADE = new InnerMapping(
                new Data(MONTHLY_RENTAL, "월세"),
                new Data(CHARTER_RENTAL, "전세"),
                new Data(SALE_HOUSE, "매매")
        );
    public final static InnerMapping LIMIT_TIME = new InnerMapping(
            new Data(0, "제한 없음"),
            new Data(10, "10분"),
            new Data(20, "20분"),
            new Data(30, "30분"),
            new Data(40, "40분"),
            new Data(50, "50분"),
            new Data(60, "60분")
    );
}
