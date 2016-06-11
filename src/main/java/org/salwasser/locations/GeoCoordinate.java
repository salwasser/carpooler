package org.salwasser.locations;

/**
 * Created by Zac on 6/11/2016.
 */
public class GeoCoordinate {
    public class BadGeoCoordinateException extends Exception {
        public BadGeoCoordinateException(String message) {
            super(message);
        }
    }

    private Double latitude;
    private Double longitude;

    private Double marginOfError = 0.000001;

    public GeoCoordinate(double latitude, double longitude) throws BadGeoCoordinateException {
        if (longitude < -180.0 ||
            longitude > 180.0 ||
            latitude < -90.0 ||
            latitude > 90.0) {
            throw new BadGeoCoordinateException(latitude + ", " + longitude + " is not a valid Geo coordinate.");
        }
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getDistance(GeoCoordinate that) {
        Double pythagA = Math.min(longitude - that.getLongitude(), 360.0 - (longitude - that.getLongitude()));
        Double pythagB = latitude - that.getLatitude();

        return Math.sqrt(pythagA * pythagA + pythagB * pythagB);
    }

    public boolean equals(GeoCoordinate that) {
        return (latitude > that.getLatitude() - marginOfError &&
                latitude < that.getLatitude() + marginOfError &&
                longitude > that.getLongitude() - marginOfError &&
                longitude < that.getLongitude() + marginOfError);
    }

    @Override
    public int hashCode() {
        return ((int)(longitude * 1000.0) * 10000) +  (int)(latitude * 100.0);
    }

    @Override
    public boolean equals(Object that) {
        return equals((GeoCoordinate)that);
    }

    @Override
    public String toString() {
        return String.format("(%.6f, %.6f)", latitude, longitude);
    }
}
