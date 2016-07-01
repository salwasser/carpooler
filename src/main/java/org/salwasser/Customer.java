package org.salwasser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zac on 6/30/2016.
 */
public class Customer implements Comparable<Customer> {
    private String lastName;
    private String firstName;
    private List<Trip> pendingTrips = new ArrayList<Trip>();

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "'" + lastName + ", " + firstName + "'";
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Customer)) {
            return false;
        }

        return (compareTo((Customer)other) == 0);
    }

    public int compareTo(Customer other) {
        int retval = lastName.compareTo(other.lastName);

        if (retval == 0) {
            return firstName.compareTo(other.firstName);
        }

        return retval;
    }


}
