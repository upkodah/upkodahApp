package com.uos.upkodah.data.local.position;

import com.uos.upkodah.data.local.gps.GeoCoordinate;

/**
 * Position 정보들은 GPS나 외부 호출에 의존하여 생성되는 경우가 많아
 * 해당 인터페이스를 적절히 활용하여 준비가 완료되는 시점에 정확한 사용이 가능하도록 해야한다.
 */
public interface PositionPreparedListener<P extends GeoCoordinate> {
    public void onPrepared(P p);
}
