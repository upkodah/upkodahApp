package com.uos.upkodah.local.map;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.DrawableRes;

import com.uos.upkodah.local.position.PositionInformation;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class UkdMapMarker extends MapPOIItem{
    private PositionInformation position;

    protected UkdMapMarker(PositionInformation position){
        this.position = position;

        // 기본적으로 좌표와 기본핀 이미지를 설정하고, 이름은 주소 전체로 합니다.
        setMarkerType(MapPOIItem.MarkerType.BluePin);
        setMapPoint(MapPoint.mapPointWithGeoCoord(position.getLatitude(), position.getLongitude()));
        setItemName(position.getPostalAddress());
        setSelectedMarkerType(MarkerType.RedPin);
        setDraggable(false);
        setShowCalloutBalloonOnTouch(true);
    }

    public static UkdMapMarker.Builder getBuilder(PositionInformation positionInformation){
        UkdMapMarker mapMarker = new UkdMapMarker(positionInformation);
        return mapMarker.new Builder();
    }

    public class Builder{
        /**
         * 마커의 이미지를 커스텀 이미지로 전환합니다.
         * @return
         */
        public UkdMapMarker.Builder setMarkerImage(@DrawableRes int id){
            UkdMapMarker.this.setMarkerType(MapPOIItem.MarkerType.CustomImage);
            UkdMapMarker.this.setSelectedMarkerType(MarkerType.CustomImage);
            UkdMapMarker.this.setCustomImageResourceId(id);
            UkdMapMarker.this.setCustomSelectedImageResourceId(id);
            UkdMapMarker.this.setCustomImageAutoscale(false);
            UkdMapMarker.this.setCustomImageAnchor(0.5f, 1.0f);

            return this;
        }

        public UkdMapMarker.Builder setMarkerPosition(float x, float y){
            UkdMapMarker.this.setCustomImageAnchor(x,y);

            return this;
        }

        /**
         * 마커의 이름을 정합니다.
         * @param name
         * @return
         */
        public UkdMapMarker.Builder setName(String name){
            UkdMapMarker.this.setItemName(name);

            return this;
        }

        public UkdMapMarker build(){
            return UkdMapMarker.this;
        }
    }

    public static abstract class Listener<PI extends PositionInformation> implements MapView.POIItemEventListener{
        public abstract void onMarkerSelected(MapView mapView, UkdMapMarker marker, PositionInformation positionInformation);
        public abstract void onMarkerBalloonSelected(MapView mapView, UkdMapMarker marker, PositionInformation positionInformation);

        @Override
        public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
            try{
                onMarkerSelected(mapView, (UkdMapMarker) mapPOIItem, (PI)((UkdMapMarker) mapPOIItem).position);
            }
            catch(ClassCastException e){
                Log.d("CAST_ERR", "POIItemListener에서 캐스팅 에러 발생");
            }
        }
        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, CalloutBalloonButtonType calloutBalloonButtonType) {
            try{
                onMarkerBalloonSelected(mapView, (UkdMapMarker) mapPOIItem, (PI)((UkdMapMarker) mapPOIItem).position);
            }
            catch(ClassCastException e){
                Log.d("CAST_ERR", "POIItemListener에서 캐스팅 에러 발생");
            }
        }

        /**
         * 빈 메소드
         */
        @Override
        @Deprecated
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

        }
        /**
         * 빈 메소드
         */
        @Override
        public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

        }
    }
}