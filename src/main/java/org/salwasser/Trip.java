package org.salwasser;

import org.salwasser.locations.Location;
import org.salwasser.structures.LinkedFIFOQueue;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Zac on 6/11/2016.
 */
public class Trip {
    private LinkedFIFOQueue<Location> itinerary;
    private Date startTime;

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

    public void addDestination(Location destination) {
        itinerary.add(destination);
    }

    public void skipDestination(Location destination) {
        itinerary.remove(destination);
    }

    public void insertDetour(Location from, Location to) {
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
