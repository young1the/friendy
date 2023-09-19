package com.chunjae.friendy.coordinate.util;

/**
 * 위도와 경도로 생성한 Coordinate 객체를 이용해서,
 * - 두 좌표 간의 거리를 구하거나,
 * - 특정 좌표에서 ?km 떨어진 SchoolAddress 데이터를 찾을 때 사용할 수 있는 함수를 제공합니다.
 */
public class CoordinateUtil {

    private static final double EARTH_RADIUS = 6371.0; // Earth's radius in kilometers
    private static final double KM_FOR_LATITUDE = (1 / (EARTH_RADIUS * 1 * (Math.PI / 180)));

    /**
     * A 좌표와, B 좌표의 거리(km)를 계산합니다.
     * @param A 좌표 A
     * @param B 좌표 B
     * @return 거리(km)
     */
    public static double getDistance(Coordinate A, Coordinate B) {
        double latitudeDistance = A.getRadianLatitude() - B.getRadianLatitude();
        double longitudeDistance = A.getRadianLongitude() - B.getRadianLongitude();
        double a = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2)
                + Math.cos(A.getRadianLatitude()) * Math.cos(B.getRadianLatitude())
                * Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    private static double getKmForLongitude(double latitude) {
        return (1 / (EARTH_RADIUS * 1 * (Math.PI / 180) * Math.cos(Math.toRadians(latitude))));
    }

    /**
     * pivot 좌표의 ?km만큼 떨어진 최대 좌표(우상단)를 구합니다.
     * @param pivot 기준점
     * @param km 떨어진 거리(km)
     * @return 최대 좌표
     */
    public static Coordinate getMaxCoordinateByKM(Coordinate pivot, double km) {
        double KM_FOR_LONGITUDE = getKmForLongitude(pivot.getLatitude());
        return new Coordinate(pivot.getLatitude() + KM_FOR_LATITUDE * km,
                pivot.getLongitude() + KM_FOR_LONGITUDE * km);
    }

    /**
     * pivot 좌표의 ?km만큼 떨어진 최소 좌표(좌하단)를 구합니다.
     * @param pivot 기준점
     * @param km 떨어진 거리(km)
     * @return 최대 좌표
     */
    public static Coordinate getMinCoordinateByKM(Coordinate pivot, double km) {
        double KM_FOR_LONGITUDE = getKmForLongitude(pivot.getLatitude());
        return new Coordinate(pivot.getLatitude() - KM_FOR_LATITUDE * km,
                pivot.getLongitude() - KM_FOR_LONGITUDE * km);
    }

    /**
     * target 좌표가 pivot 좌표의 ?km 내에 있는 좌표인 지 구합니다.
     * @param pivot 기준
     * @param target 목표
     * @param km 반경(km)
     * @return target이 pivot의 ?km 안에 있으면 true, 아니면 false
     */
    public static boolean isInArea(Coordinate pivot, Coordinate target, double km) {
        double distance = CoordinateUtil.getDistance(pivot, target);
        return distance <= km;
    }

}
