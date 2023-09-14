package com.chunjae.friendy.util.coordinate;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Coordinate {

    private final double latitude;
    private final double longitude;

    /**
     * 문자열로 위도와 경도를 입력해서 Coordinate 객체를 생성합니다.
     * @param latitude : 위도
     * @param longitude : 경도
     */
    public Coordinate(String latitude, String longitude) {
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
    }

    Coordinate(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getRadianLatitude() {
        return Math.toRadians(this.latitude);
    }

    public double getRadianLongitude() {
        return Math.toRadians(this.longitude);
    }

}
