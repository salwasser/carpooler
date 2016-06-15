package org.salwasser;

import org.salwasser.locations.Location;
import org.salwasser.structures.LinkedFIFOQueue;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Zac on 6/11/2016.
 */
public class Trip implements Comparable<Trip>, Iterable<Location> {
    private LinkedFIFOQueue<Location> itinerary;
    private Date startTime;
    private Double distance = null;

    public Trip() {
        itinerary = new LinkedFIFOQueue<Location>();
        startTime = null;
    }

    public Trip(ArrayList<Location> itinerary) {
        this();
        this.itinerary.addAll(itinerary);
    }

    public Trip(Date startTime, ArrayList<Location> itinerary) {
        this(itinerary);
        this.startTime = startTime;
    }

    public Trip(Date startTime) {
        this();
        this.startTime = startTime;
    }

    public int compareTo(Trip other) {
        return getDistance().compareTo(other.getDistance());
    }

    public Double getDistance() {
        if (distance == null) {
            Location loc_a = null;
            distance = 0.0;
            for (Location loc_b : itinerary) {
                if (loc_a != null) {
                    distance += loc_a.distanceFrom(loc_b);
                }
                loc_a = loc_b;
            }
        }

        return distance;
    }

    public Iterator<Location> iterator() {
        return itinerary.iterator();
    }

    public void merge(Trip other) {
        for (Location otherLoc : other) {
            this.addDestination(otherLoc);
        }
    }


    public void addDestination(Location destination) {
        distance = null;
        if (itinerary.size() == 0) {
            itinerary.add(destination);
            return;
        }

        Location loc_a = null;
        Location fromLoc = itinerary.getLast();
        Double minDistance = destination.distanceFrom(fromLoc);

        for (Location loc_b : itinerary) {
            if (loc_a != null) {
                Double newSpanDistance = loc_a.distanceFrom(destination) + loc_b.distanceFrom(destination);
                Double oldSpanDistance = loc_a.distanceFrom(loc_b);
                Double spanDistanceDelta = newSpanDistance - oldSpanDistance;

                if (spanDistanceDelta < minDistance) {
                    minDistance = spanDistanceDelta;
                    fromLoc = loc_a;
                }
            }
            loc_a = loc_b;
        }

        if (fromLoc.equals(itinerary.getLast())) {
            itinerary.add(destination);
        } else {
            itinerary.add(fromLoc, destination);
        }
    }

    public void skipDestination(Location destination) {
        distance = null;
        itinerary.remove(destination);
    }

    public void insertDetour(Location from, Location to) {
        distance = null;
        itinerary.add(from, to);
    }

    @Override
    public String toString() {
        if (startTime == null) {
            return itinerary.toString();
        } else {
            return "Starting at: " + startTime + ": " + itinerary;
        }
    }
}
