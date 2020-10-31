package com.uos.upkodah.local.position.group;

import com.uos.upkodah.local.position.EstateInformation;
import com.uos.upkodah.local.position.PositionInformation;
import com.uos.upkodah.local.position.RegionInformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 해당 클래스는 수신받은 EstateInformation들을 지역별로 분류하는 역할을 수행한다.
 */
public class EstateGrouper {
    private List<RegionInformation> classifiedRegions;

    public EstateGrouper(List<EstateInformation> estates){
        // 생성자에서 Estate들을 그룹화한다.
        classifiedRegions = classifyEstate(estates, new ArrayList<String>());
    }

    /**
     *
     */
    public List<RegionInformation> classifyEstate(List<EstateInformation> estates, List<String> regions){
        HashMap<String, RegionInformation> regionInformations = new HashMap<>();
        int depth = 0;
        List<String> subRegions;

        for(EstateInformation estate : estates){
            // 서브 지역 초기화
            subRegions = new ArrayList<>(regions);
            String region = estate.getRegion(depth);
            subRegions.add(region);

            // 만약 해당 지역의 키가 존재하지 않으면 새로 만들어 삽입한다.
            if(!regionInformations.containsKey(region)){
                regionInformations.put(region, new RegionInformation(subRegions));
            }

            // 키와 맞는 RegionInformation에 해당 Estate를 삽입한다.
            regionInformations.get(region).addEstate(estate);
        }

        // 배열화시킨다.
        List<RegionInformation> regionInformationList = new ArrayList<RegionInformation>(regionInformations.values());

        // 앞서 만든 RegionInformation을 하나씩 가져와
        // 내부 원소를 전부 빼고 정렬시킨다.
        for(RegionInformation regionInformation : regionInformationList){
            // 재귀호출하여 바로 하위 Estate들을 정리시켜 지역정보를 전부 가져온다.
            List<? extends PositionInformation> tmpList = classifyEstate(regionInformation.getEstates(), regionInformation.getRegions());

            // 해당 지역 내 Estate들을 전부 비운다.
            regionInformation.clearEstates();

            // 정리된 것을 다시 넣는다.
            for(PositionInformation estate : tmpList){
                regionInformation.addEstate(estate);
            }
        }

        return regionInformationList;
    }

    public List<RegionInformation> getClassifiedRegions() {
        return classifiedRegions;
    }
}
