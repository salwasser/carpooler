package org.salwasser;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.salwasser.locations.GeoCoordinate;
import org.salwasser.locations.Location;
import org.salwasser.structures.BinarySearchTree;
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
        {
            JumpingOnPoints jp = new JumpingOnPoints();
            int[] params = {0, 0, 5, 100, 0, 0, 5, 100, 0, 0, 10, 100, 1, 1, 0, 100};
            long retval = 150000000000000l;
            assertTrue(jp.sumOfDistances(2, 0, params) == retval);
            System.err.println("Passed - (" + retval + ")");
        }

        {
            JumpingOnPoints jp = new JumpingOnPoints();
            int[] params = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
            long retval = 18;
            assertTrue(jp.sumOfDistances(3, 1, params) == retval);
            System.err.println("Passed - (" + retval + ")");
        }

        {
            JumpingOnPoints jp = new JumpingOnPoints();
            int[] params = {0, 1, 1, 100, 0, 1, 1, 100, 1, 1, 0, 100, 0, 0, 1, 2};
            long retval = 6;
            assertTrue(jp.sumOfDistances(4, 0, params) == retval);
            System.err.println("Passed - (" + retval + ")");
        }

        {
            JumpingOnPoints jp = new JumpingOnPoints();
            int[] params = {0, 1, 5, 15, 0, 1, 5, 10, 0, 0, 0, 1,1,1,1,3};
            long retval = 8;
            assertTrue(jp.sumOfDistances(6, 0, params) == retval);
            System.err.println("Passed - (" + retval + ")");
        }

        {
            JumpingOnPoints jp = new JumpingOnPoints();
            int[] params = {0, 1, 1, 1000000000, 0, 1, 1, 1000000000, 1, 1, 0, 1000000000, 999999999, 1, 0, 1000000000};
            long retval = 1799969998200030000l;
            assertTrue(jp.sumOfDistances(60000, 0, params) == retval);
            System.err.println("Passed - (" + retval + ")");
        }

        {
            JumpingOnPoints jp = new JumpingOnPoints();
            int[] params = {11111, 11111, 111111, 11111111, 12121, 12111, 13131, 11111111, 13, 14444, 44312, 222211, 13131, 328655, 11373, 999999993};
            long retval = 738940004832l;
            assertTrue(jp.sumOfDistances(1000, 286, params) == retval);
            System.err.println("Passed - (" + retval + ")");
        }

        BinarySearchTree<Customer> bst = new BinarySearchTree<Customer>();

        bst.insert(new Customer("Zac", "Salwasser"));
        bst.insert(new Customer("Rebekah", "Splaine"));
        bst.insert(new Customer("Niko", "Salwasser"));
        bst.insert(new Customer("Calvin", "Salwasser"));
        bst.insert(new Customer("William", "Levine"));
        bst.insert(new Customer("Chouteau", "Levine"));
        System.err.println(bst.debugString());
        System.err.println("* * *");
        System.err.println(bst.toString());
        System.err.println("* * *");
        System.err.println(bst.asSortedList());
        System.err.println("* * *");
        System.err.println("Contains Calvin Salwasser? " + bst.contains(new Customer("Calvin", "Salwasser")));
        System.err.println("Contains Rebekah Splaine? " + bst.contains(new Customer("Rebekah", "Splaine")));
        System.err.println("Contains Mark Salwasser? " + bst.contains(new Customer("Mark", "Salwasser")));

        bst.remove(new Customer("Calvin", "Salwasser"));
        System.err.println(bst.debugString());
        System.err.println("* * *");
        System.err.println(bst.toString());
        System.err.println("* * *");
        System.err.println(bst.asSortedList());
        System.err.println("* * *");
        System.err.println("Contains Calvin Salwasser? " + bst.contains(new Customer("Calvin", "Salwasser")));

        bst.remove(new Customer("Mark", "Salwasser"));
        bst.remove(new Customer("Chouteau", "Levine"));
        System.err.println(bst.debugString());
        System.err.println("* * *");
        System.err.println(bst.toString());
        System.err.println("* * *");
        System.err.println(bst.asSortedList());
        System.err.println("* * *");
        System.err.println("Contains Calvin Salwasser? " + bst.contains(new Customer("Calvin", "Salwasser")));
        System.err.println("Contains Chouteau Levine? " + bst.contains(new Customer("Chouteau", "Levine")));
        System.err.println("Contains Rebekah Splaine? " + bst.contains(new Customer("Rebekah", "Splaine")));
        System.err.println("Contains Mark Salwasser? " + bst.contains(new Customer("Mark", "Salwasser")));

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

        assertTrue(true);
    }
}
