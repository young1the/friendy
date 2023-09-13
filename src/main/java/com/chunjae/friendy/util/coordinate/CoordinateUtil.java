package com.chunjae.friendy.util.coordinate;

public class CoordinateUtil {

    private static final double EARTH_RADIUS = 6371.0; // Earth's radius in kilometers
    private static final double KM_FOR_LATITUDE = (1 / (EARTH_RADIUS * 1 * (Math.PI / 180)));

    public static double getDistance(Coordinate A, Coordinate B) {
        double latitudeDistance = A.getRadianLatitude() - B.getRadianLatitude();
        double longitudeDistance = A.getRadianLongitude() - B.getRadianLongitude();
        double a = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2)
                + Math.cos(A.getRadianLatitude()) * Math.cos(B.getRadianLatitude())
                * Math.sin(longitudeDistance / 2) * Math.sin(longitudeDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    public static double getKmForLongitude(double latitude) {
        return (1 / (EARTH_RADIUS * 1 * (Math.PI / 180) * Math.cos(Math.toRadians(latitude))));
    }

    public static Coordinate getMaxCoordinateByKM(Coordinate pivot, double km) {
        double KM_FOR_LONGITUDE = getKmForLongitude(pivot.getLatitude());
        return new Coordinate(pivot.getLatitude() + KM_FOR_LATITUDE * km,
                pivot.getLongitude() + KM_FOR_LONGITUDE * km);
    }

    public static Coordinate getMinCoordinateByKM(Coordinate pivot, double km) {
        double KM_FOR_LONGITUDE = getKmForLongitude(pivot.getLatitude());
        return new Coordinate(pivot.getLatitude() - KM_FOR_LATITUDE * km,
                pivot.getLongitude() - KM_FOR_LONGITUDE * km);
    }

    public static boolean isInArea(Coordinate pivot, Coordinate target, double km) {
        double distance = CoordinateUtil.getDistance(pivot, target);
        if ()
    }

}
