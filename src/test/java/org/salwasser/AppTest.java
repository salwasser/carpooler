package org.salwasser;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.salwasser.locations.GeoCoordinate;
import org.salwasser.locations.Location;
import org.salwasser.structures.SortableArrayList;

import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {

        SortableArrayList<Integer> numberSorter;

        numberSorter = new SortableArrayList<Integer>();
        for (int idx = 100; idx > 0; idx--) {
            numberSorter.add(idx);
        }
        numberSorter.quickSort();
        System.err.println(numberSorter);

        numberSorter = new SortableArrayList<Integer>();
        for (int idx = 100; idx > 0; idx--) {
            numberSorter.add(idx);
        }
        numberSorter.insertionSort();
        System.err.println(numberSorter);

        numberSorter = new SortableArrayList<Integer>();
        for (int idx = 0; idx < 100; idx++) {
            numberSorter.add(idx);
        }
        numberSorter.quickSort(true);
        System.err.println(numberSorter);

        numberSorter = new SortableArrayList<Integer>();
        for (int idx = 0; idx < 100; idx++) {
            numberSorter.add(idx);
        }
        numberSorter.insertionSort(true);
        System.err.println(numberSorter);
        
        try {
            Trip testTrip = new Trip(new Date(System.currentTimeMillis()));
            testTrip.addDestination(new Location(new GeoCoordinate(0.0, 0.0)));
            testTrip.addDestination(new Location(new GeoCoordinate(1.0, 1.0)));
            testTrip.addDestination(new Location(new GeoCoordinate(3.0, 3.0)));
            testTrip.addDestination(new Location(new GeoCoordinate(4.0, 4.0)));
            System.err.println(testTrip.toString());

            testTrip.insertDetour(new Location(new GeoCoordinate(1.0, 1.0)),
                                  new Location(new GeoCoordinate(2.0, 2.0)));
            System.err.println(testTrip.toString());

            testTrip.addDestination(new Location(new GeoCoordinate(5.0, -1.0)));
            System.err.println(testTrip.toString());

            testTrip.addDestination(new Location(new GeoCoordinate(5.0, -2.0)));
            System.err.println(testTrip.toString());

            testTrip.skipDestination(new Location(new GeoCoordinate(0.0, 0.0)));
            testTrip.skipDestination(new Location(new GeoCoordinate(5.0, -2.0)));
            testTrip.skipDestination(new Location(new GeoCoordinate(4.0, 4.0)));
            System.err.println(testTrip.toString());

            testTrip.skipDestination(new Location(new GeoCoordinate(2.0, 2.0)));
            testTrip.skipDestination(new Location(new GeoCoordinate(3.0, 3.0)));
            testTrip.addDestination(new Location(new GeoCoordinate(5.0, 5.0)));
            testTrip.addDestination(new Location(new GeoCoordinate(1.01, 1.01)));

            System.err.println(testTrip.toString());
        } catch (GeoCoordinate.BadGeoCoordinateException b) {
            throw new RuntimeException(b);
        }
        assertTrue( false );
    }
}
