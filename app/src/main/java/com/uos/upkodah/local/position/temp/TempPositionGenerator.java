package com.uos.upkodah.local.position.temp;

import android.content.Context;

import com.android.volley.Response;
import com.uos.upkodah.local.position.EstateInformation;
import com.uos.upkodah.local.position.GridRegionInformation;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.local.position.RegionInformation;
import com.uos.upkodah.local.position.SubRegionInformation;
import com.uos.upkodah.server.extern.KakaoAPIRequest;
import com.uos.upkodah.server.extern.parser.CoordToRegionParser;
import com.uos.upkodah.server.extern.parser.SearchKeyworkParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TempPositionGenerator {
    private Context context;

    private double lon;
    private double lat;

    private List<PositionInformation> positionList;
    private List<EstateInformation> estateList;


    public TempPositionGenerator(Context context, double longitude, double latitude){
        this.context = context;
        this.lon = longitude;
        this.lat = latitude;

        // 먼저, 카카오 키워드 API로 카페 목록을 가져온다.
        KakaoAPIRequest keywordRequest = KakaoAPIRequest.getSearchKeywordRequest("경찰서", lon, lat, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SearchKeyworkParser parser = SearchKeyworkParser.getInstance(response);

                if(parser!=null){
                    positionList = parser.getPositionList();
                    estateList = new ArrayList<>();

                    // 매물 목록을 생성한다.
                    setEstateList(positionList);

                    // 매물마다 행정구역 정보를 확인한다.
                    for(EstateInformation e : estateList){
                        setEstateRegionData(e);
                    }
                }
            }
        }, null);
        keywordRequest.request(context);
    }


    public void setEstateList(List<PositionInformation> positionList){
        estateList = new ArrayList<>();

        // 매물 목록을 생성한다.
        for(PositionInformation p : positionList){
            estateList.add(new EstateInformation(p));
        }
    }
    public void setEstateRegionData(final EstateInformation estate){
        KakaoAPIRequest regionRequest = KakaoAPIRequest.getCoordToRegionRequest(
                estate.getLongitude(),
                estate.getLatitude(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // 지역분류 설정
                        CoordToRegionParser parser = CoordToRegionParser.getInstance(response);
                        estate.setRegion(parser.getRegion(1), parser.getRegion(2));

                        // 이걸 SubRegion에 넣는다.
                        setSubRegionMap(estate);
                    }
                },
                null
        );
        regionRequest.request(context);
    }
    private HashMap<String, SubRegionInformation> subRegionMap = new HashMap<>();
    private HashMap<String, RegionInformation> regionMap = new HashMap<>();

    public void setSubRegionMap(EstateInformation e){
        // 먼저, 해당 Estate의 동 정보를 알아낸다.
        String key = e.getRegion();

        // 이 정보를 키로 하여 subRegion을 찾는다.
        if(subRegionMap.containsKey(key)){
            // 있다면, 해당 subRegion의 0번째 Grig에 넣는다.
            SubRegionInformation subRegion = subRegionMap.get(key);
            GridRegionInformation gridRegion = subRegion.getSubInfoList().get(0);
            gridRegion.addSubInformation(e);
        }
        else{
            // 없다면, SubRegionInformation과 GridInformation을 새로 만든다.
            GridRegionInformation gridRegion = new GridRegionInformation(e.getRegion());
            gridRegion.addSubInformation(e);
            SubRegionInformation subRegion = new SubRegionInformation(e.getRegion());
            subRegion.setParentId(e.getGu());
            subRegion.addSubInformation(gridRegion);

            subRegionMap.put(key, subRegion);
        }
    }
    public void setTotalRegion(){
        if(regionMap.size() > 0) return;
        // 먼저, 전체 subRegion을 가져온다.
        List<SubRegionInformation> subRegions = new ArrayList<>(subRegionMap.values());

        for(SubRegionInformation s : subRegions){
            // 먼저, 해당 subRegion의 구 정보를 읽는다.
            String key = s.getParentId();

            // 이제 해당 키에 맞는 region이 있는지 확인
            if(regionMap.containsKey(key)){
                // 있으면 넣는다.
                regionMap.get(key).addSubInformation(s);
            }
            else{
                // 없으면 만들어서 넣는다.
                RegionInformation region = new RegionInformation(s.getParentId());
                region.addSubInformation(s);
                regionMap.put(key, region);

            }
        }
    }
    public void checkEstates(){
        for(EstateInformation e : estateList){
            System.out.println(e);
        }
    }
    public void checkSubRegions(){
        List<SubRegionInformation> subRegions = new ArrayList<>(subRegionMap.values());
        for(SubRegionInformation e : subRegions){
            System.out.println(e);
        }
    }
    public void checkRegions(){
        List<RegionInformation> regions = new ArrayList<>(regionMap.values());
        for(RegionInformation e : regions){
            System.out.println(e);
        }
    }

    public List<RegionInformation> getTotalRegion(){
        return new ArrayList<RegionInformation>(regionMap.values());
    }
}
