package com.uos.upkodah.server.extern;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uos.upkodah.data.local.gps.GeoCoordinate;

import java.util.HashMap;
import java.util.Map;

public class KakaoAPIRequest extends StringRequest {
    protected KakaoAPIRequest(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }
    protected final static String SEARCH_ADDR_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    protected final static String COORD_TO_REGIONCODE_URL = "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json";
    protected final static String COORD_TO_ADDRCODE_URL = "https://dapi.kakao.com/v2/local/geo/coord2address.json";
    protected final static String TRANS_COORD_URL = "https://dapi.kakao.com/v2/local/geo/transcoord.json";
    protected final static String SEARCH_KEYWORD_URL = "https://dapi.kakao.com/v2/local/search/keyword.json";
    protected final static String SEARCH_CATEGORY_URL = "https://dapi.kakao.com/v2/local/search/category.json";

//    private final static String REST_API_KEY = "a92b21b75ce1b2ad1b8ded4dcfdc1f41";
    private final static String REST_API_KEY = "bf08f1b56f8d605a2100093e2850379f";

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization","KakaoAK "+REST_API_KEY);

        return headers;
    }
    public static RequestQueue requestQueue = null;
    public synchronized void request(Context context){
        if(requestQueue==null) requestQueue = Volley.newRequestQueue(context);

        requestQueue.add(this);
    }

    public static KakaoAPIRequest getSearchAddrRequest(@NonNull String query, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener){
        return new SearchAddrRequest(SearchAddrRequest.getRequestURL(query),listener,errorListener);
    }
    public static KakaoAPIRequest getSearchKeywordRequest(@NonNull String query, GeoCoordinate coord, int radius, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener){
        return new SearchKeywordRequest(SearchKeywordRequest.getRequestURL(query,coord,radius),listener,errorListener);
    }
    public static KakaoAPIRequest getSearchKeywordRequest(@NonNull String query, GeoCoordinate coord, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener){
        return new SearchKeywordRequest(SearchKeywordRequest.getRequestURL(query,coord),listener,errorListener);
    }
    public static KakaoAPIRequest getSearchCategoryRequest(@NonNull String category, GeoCoordinate coord, int radius, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener){
        return new SearchCategoryRequest(SearchCategoryRequest.getRequestURL(category,coord, radius),listener,errorListener);
    }
    public static KakaoAPIRequest getSearchCategoryRequest(@NonNull String category, GeoCoordinate coord,  Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener){
        return new SearchCategoryRequest(SearchCategoryRequest.getRequestURL(category,coord),listener,errorListener);
    }
    public static KakaoAPIRequest getCoordToAddrRequest(GeoCoordinate coord, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener){
        return new CoordToAddrRequest(CoordToAddrRequest.getRequestURL(coord),listener,errorListener);
    }
    public static KakaoAPIRequest getCoordToRegionRequest(GeoCoordinate coord, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener){
        return new CoordToRegionRequest(CoordToRegionRequest.getRequestURL(coord),listener,errorListener);
    }
}

/**
 * 주소를 이용해 위도, 경도를 구하고, 도로명 주소/지번 주소 변환을 위해 활용한다.
 */
class SearchAddrRequest extends KakaoAPIRequest{
    /*
    String:query(필수) : 검색을 원하는 질의어
    Integer:page : 결과 페이지 번호 1~45 사이, 기본 값 1
    Integer:AddressSize : 한 페이지에 보여질 문서의 개수, 1~30 사이, 기본 값 10
     */
    protected static String getRequestURL(String query){
        String requestURL = SEARCH_ADDR_URL+"?"
                +"query="+query;
        return requestURL;
    }
    SearchAddrRequest(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }
}

/**
 * 키워드를 이용해 원하는 장소들을 찾아낼 때 사용하는 API
 */
class SearchCategoryRequest extends KakaoAPIRequest{
    /*
    String:query(필수) = 검색 할 키워드
    String:category_group_code = 카테고리 그룹 코드. 결과를 카테고리로 필터링을 원하는 경우 사용
    Double:x = 중심 좌표의 X값 혹은 longitude. 특정 지역을 중심으로 검색하려고 할 경우 radius와 함께 사용 가능
    Double:y = 중심 좌표의 X값 혹은 longitude. 특정 지역을 중심으로 검색하려고 할 경우 radius와 함께 사용 가능
    Integer:radius = 중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 중심좌표로 쓰일 x,y와 함께 사용
                    단위 meter, 0~20000 사이의 값
    String:rect = 사각형 범위내에서 제한 검색을 위한 좌표. 지도 화면 내 검색시 등 제한 검색에서 사용 가능
                "좌측 X 좌표,좌측 Y 좌표,우측 X 좌표,우측 Y 좌표" 형식
    Integer:page = 결과 페이지 번호, 1~45 사이의 값, 기본 값 1
    Integer:size = 한 페이지에 보여질 문서의 개수, 1~15 사이의 값, 기본 값 15
    String:sort = 결과 정렬 순서, distance 정렬을 원할 때는 기준 좌표로 쓰일 x, y와 함께 사용. distance 또는 accuracy, 기본 accuracy
     */
    protected static String getRequestURL(String category, GeoCoordinate coord, int radius){
        String requestURL = SEARCH_CATEGORY_URL+"?"
                +"category_group_code="+category+"&"
                +"x="+coord.getLongitude()+"&"
                +"y="+coord.getLatitude()+"&"
                +"radius="+radius;
        return requestURL;
    }
    protected static String getRequestURL(String category, GeoCoordinate coord){
        String requestURL = SEARCH_CATEGORY_URL+"?"
                +"category_group_code="+category+"&"
                +"x="+coord.getLongitude()+"&"
                +"y="+coord.getLatitude();
        return requestURL;
    }
    SearchCategoryRequest(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }
}
/**
 * 키워드를 이용해 원하는 장소들을 찾아낼 때 사용하는 API
 */
class SearchKeywordRequest extends KakaoAPIRequest{
    /*
    String:category_group_code(필수) = 카테고리 그룹 코드. 결과를 카테고리로 필터링을 원하는 경우 사용
    Double:x = 중심 좌표의 X값 혹은 longitude. 특정 지역을 중심으로 검색하려고 할 경우 radius와 함께 사용 가능
    Double:y = 중심 좌표의 X값 혹은 longitude. 특정 지역을 중심으로 검색하려고 할 경우 radius와 함께 사용 가능
    Integer:radius = 중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 중심좌표로 쓰일 x,y와 함께 사용
                    단위 meter, 0~20000 사이의 값
    String:rect = 사각형 범위내에서 제한 검색을 위한 좌표. 지도 화면 내 검색시 등 제한 검색에서 사용 가능
                "좌측 X 좌표,좌측 Y 좌표,우측 X 좌표,우측 Y 좌표" 형식
    Integer:page = 결과 페이지 번호, 1~45 사이의 값, 기본 값 1
    Integer:size = 한 페이지에 보여질 문서의 개수, 1~15 사이의 값, 기본 값 15
    String:sort = 결과 정렬 순서, distance 정렬을 원할 때는 기준 좌표로 쓰일 x, y와 함께 사용. distance 또는 accuracy, 기본 accuracy
     */
    protected static String getRequestURL(String query, GeoCoordinate coord, int radius){
        String requestURL = SEARCH_KEYWORD_URL+"?"
                +"query="+query+"&"
                +"x="+coord.getLongitude()+"&"
                +"y="+coord.getLatitude()+"&"
                +"radius="+radius;
        return requestURL;
    }
    protected static String getRequestURL(String query, GeoCoordinate coord){
        String requestURL = SEARCH_KEYWORD_URL+"?"
                +"query="+query+"&"
                +"x="+coord.getLongitude()+"&"
                +"y="+coord.getLatitude();
        return requestURL;
    }
    SearchKeywordRequest(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }
}

/**
 * 좌표를 이용해 해당 좌표의 주소를 얻어내는 API
 */
class CoordToAddrRequest extends KakaoAPIRequest{
    /*
    Double:x(필수) =  x 좌표로 경위도인 경우 longitude
    Double:y(필수) = y 좌표로 경위도인 경우 latitude
    String:input_coord = x, y 로 입력되는 값에 대한 좌표 체계, 기본 값은 WGS84. 지원 좌표계: WGS84, WCONGNAMUL, CONGNAMUL, WTM, TM
     */
    protected static String getRequestURL(GeoCoordinate coord){
        String requestURL = COORD_TO_ADDRCODE_URL+"?"
                +"x="+coord.getLongitude()+"&"
                +"y="+coord.getLatitude();
        return requestURL;
    }
    CoordToAddrRequest(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }
}

/**
 * 좌표를 이용해 해당 좌표의 행정구역 정보를 얻어내는 API
 */
class CoordToRegionRequest extends KakaoAPIRequest{
    /*
    Double:x(필수) =  x 좌표로 경위도인 경우 longitude
    Double:y(필수) = y 좌표로 경위도인 경우 latitude
    String:input_coord = x, y 로 입력되는 값에 대한 좌표 체계, 기본 값은 WGS84. 지원 좌표계: WGS84, WCONGNAMUL, CONGNAMUL, WTM, TM
     */
    protected static String getRequestURL(GeoCoordinate coord){
        String requestURL = COORD_TO_REGIONCODE_URL+"?"
                +"x="+coord.getLongitude()+"&"
                +"y="+coord.getLatitude();
        return requestURL;
    }
    CoordToRegionRequest(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }
}