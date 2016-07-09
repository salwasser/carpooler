package org.salwasser;

import java.util.*;

public class JumpingOnPoints {
    private int X[];
    private int Y[];
    private int P[];
    private int T[];

    private Map<Integer, Set<Integer>> singlePassPoints = new HashMap<Integer, Set<Integer>>();

    private Map<Integer, Map<Integer, Long>> connections = new HashMap<Integer, Map<Integer, Long>>();

    private boolean adjacent(int from, int to) {
        //There is an arrow from point i to point j if and only if
        //min(|X[i] - X[j]|, |Y[i] - Y[j]|) <= P[i]. Note that the property uses the value P[i] of the source point.
        //If P[i] and P[j] differ, it is possible that there is an arrow in one direction but not in the other.

        return (Math.min(Math.abs(X[from] - X[to]), Math.abs(Y[from] - Y[to])) <= P[from]);
    }

    private int adjacentCost(int from, int to) {
        return Math.max(T[from], T[to]);
    }

    private void graphGen(int N, int[] params) {
        X = new int[N];
        Y = new int[N];
        P = new int[N];
        T = new int[N];

        X[0] = params[0];
        Y[0] = params[4];
        P[0] = params[8];
        T[0] = params[12];
        for (int i = 1; i < N; i++) {
            X[i] = (X[i - 1] * params[1] + params[2]) % params[3];
            Y[i] = (Y[i - 1] * params[5] + params[6]) % params[7];
            P[i] = (P[i - 1] * params[9] + params[10]) % params[11];
            T[i] = (T[i - 1] * params[13] + params[14]) % params[15];
        }
    }

    private class PathTooLongException extends Exception {
    }

    private long getMinDistance(int from, int to, Set<Integer> path) throws PathTooLongException {
        //System.err.println(indent + "F: " + from + ", T: " + to + ", P: " + path);
        //if (path.size() > 128) {
        //    throw new PathTooLongException();
        //}

        path.add(from);
        long disconnected = (long)(1.5 * Math.pow(10.0, 14.0));

        if (!connections.containsKey(from)) {
            connections.put(from, new HashMap<Integer, Long>());
        }

        if (connections.get(from).containsKey(to)) {
            //System.err.println("Returning " + connections.get(from).get(to));
            return connections.get(from).get(to);
        }

        if (!singlePassPoints.containsKey(from)) {
            singlePassPoints.put(from, new HashSet<Integer>());
        }
        if (singlePassPoints.get(from).contains(to)) {
            System.err.println("Called more than once with the same parameters, but resulted in non-trivial branching.");
        }
        singlePassPoints.get(from).add(to);

        for (int index = 0; index < X.length; index++) {
            if (!path.contains(index) && adjacent(from, index)) {
                if (connections.get(from).containsKey(index)) {
                    int adjacentCost = adjacentCost(from, index);
                    if (adjacentCost < connections.get(from).get(index)) {
                        connections.get(from).put(index, (long)adjacentCost);
                    }
                } else {
                    connections.get(from).put(index, (long)adjacentCost(from, index));
                }
            }
        }

        //System.err.println(indent + "Known connections: " + connections.get(from));

        Map<Integer, Long> newWaypoints = new HashMap<Integer, Long>();
        for (int possibleWaypoint : connections.get(from).keySet()) {
            if (!path.contains(possibleWaypoint)) {
                if (!connections.get(from).containsKey(to) ||
                        connections.get(from).get(possibleWaypoint) < connections.get(from).get(to)) {
                    long distance = Math.min(connections.get(from).get(possibleWaypoint) +
                                    getMinDistance(possibleWaypoint, to, path),
                            disconnected);
                    path.remove(possibleWaypoint);
                    if (!newWaypoints.containsKey(to) ||
                        distance < newWaypoints.get(to)) {
                            newWaypoints.put(to, distance);
                    }
                }
            }
        }

        //System.err.println(indent + "New waypoints: " + newWaypoints);
        for (Integer newWaypointIndex : newWaypoints.keySet()) {
            if (!connections.get(from).containsKey(newWaypointIndex) ||
                newWaypoints.get(newWaypointIndex) < connections.get(from).get(newWaypointIndex)) {
                connections.get(from).put(newWaypointIndex, newWaypoints.get(newWaypointIndex));
            }
        }
        //System.err.println(indent + "Known connections: " + connections.get(from));

        if (connections.get(from).keySet().isEmpty()) {
            connections.get(from).put(to, disconnected);
        }

        long retval = connections.get(from).get(to);
        //System.err.println(indent + "Returning " + retval);
        return retval;
    }

    public long sumOfDistances(int N, int S, int[] params) {
        graphGen(N, params);
        long retval = 0;
        Set<Integer> tryAgain = new HashSet<Integer>();
        for (int i = 0; i < N; i++) {
            if (i != S) {
                tryAgain.add(i);
            }
        }
        long j = 0;
        while (!tryAgain.isEmpty()) {
            Set<Integer> toTryAgain = new HashSet<Integer>(tryAgain);
            //System.err.println("Computing " + toTryAgain.size() + " values on this pass.");
            for (int i : toTryAgain) {
                try {
                    long subDistance = getMinDistance(S, i, new HashSet<Integer>());
                    retval += subDistance;
                    tryAgain.remove(i);
                } catch (PathTooLongException p) {
                    tryAgain.add(i);
                }
                j++;
                /*
                if (j % 100 == 0) {
                    System.err.println("Partial progress: " + retval);
                }
                */
            }
        }

        return retval;
    }
}