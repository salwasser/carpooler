package org.salwasser.locations;

/**
 * Created by Zac on 6/11/2016.
 */
public class Location {
    private GeoCoordinate geo = null;

    public Location(GeoCoordinate geo) {
        this.geo = geo;
    }

    public GeoCoordinate asGeoCoordinate() {
        return geo;
    }

    @Override
    public String toString() {
        if (geo == null) {
            return "null";
        } else {
            return geo.toString();
        }
    }

    public boolean equals(Location that) {
        return geo.equals(that.asGeoCoordinate());
    }

    @Override
    public int hashCode() {
        return geo.hashCode();
    }

    @Override
    public boolean equals(Object that) {
        return equals((Location)that);
    }


}
