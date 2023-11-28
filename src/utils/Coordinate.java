package utils;

public class Coordinate {
    private final double lat; // both stored in degrees
    private final double lng;

    public Coordinate(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public double getDistanceTo(Coordinate other) {
        double lat1 = Math.toRadians(this.lat);
        double lat2 = Math.toRadians(other.lat);
        double lng1 = Math.toRadians(this.lng);
        double lng2 = Math.toRadians(other.lng);

        double dLat = lat2 - lat1;
        double dLng = lng2 - lng1;

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.pow(Math.sin(dLng / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return 6371 * c;
    }


    @Override
    public String toString() {
        return "(" + lat + ", " + lng + ")";
    }
}
