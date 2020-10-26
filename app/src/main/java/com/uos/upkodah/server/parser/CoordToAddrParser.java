package com.uos.upkodah.server.parser;

import java.util.List;

/**
 * 좌표를 주소로 변환하는 API 사용 시 넘어오는 Response를 파싱해서 저장한다.
 */
public class CoordToAddrParser {
    public CoordToAddrParser(String response){
        //                // 결과는 JSON이므로, 적절히 변환한다.
//                try {
//                    JSONObject jobj = new JSONObject(response).getJSONArray("documents").getJSONObject(0).getJSONObject("address");
//
//                    origin.postalAddress = jobj.getString("address_name");
//
//                    for(int i=1;i<=3;i++){
//                        String region = jobj.getString("region_"+i+"depth_name");
//                        if(region.isEmpty()) break;
//
//                        origin.region.add(region);
//                    }
//                } catch (JSONException e) {
//                }
//                finally {
//                    // 지정된 리스너를 실행시킵니다.
//                    PositionInformationProxy.this.listener.onResponse();
//                }
        System.out.println(response);
        postalAddress = response;
    }

    private String postalAddress;
    public String getPostalAddress(){
        return postalAddress;
    }
    public List<String> getRegions(){
        return null;
    }
}
