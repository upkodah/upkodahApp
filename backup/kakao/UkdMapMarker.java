package com.uos.upkodah.local.map.kakao;

import android.util.Log;

import androidx.annotation.DrawableRes;

import com.uos.upkodah.local.position.PositionInformation;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class UkdMapMarker extends MapPOIItem{
    public PositionInformation getPosition() {
        return position.clone();
    }
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
            UkdMapMarker.this.setMarkerType(MarkerType.BluePin);
            UkdMapMarker.this.setSelectedMarkerType(MarkerType.RedPin);
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


}