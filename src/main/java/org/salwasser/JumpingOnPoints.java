package org.salwasser;

import java.util.HashMap;
import java.util.Map;

public class JumpingOnPoints {
    private int X[];
    private int Y[];
    private int P[];
    private int T[];

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

    private long getMinDistance(int from, int to) {
        long disconnected = (long)(1.5 * Math.pow(10.0, 14.0));

        if (!connections.containsKey(from)) {
            connections.put(from, new HashMap<Integer, Long>());
        }

        if (connections.get(from).containsKey(to)) {
            return connections.get(from).get(to);
        }

        for (int index = 0; index < X.length; index++) {
            if (from != index && adjacent(from, index)) {
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

        for (int possibleWaypoint : connections.get(from).keySet()) {
            if (!connections.get(from).containsKey(to) ||
                connections.get(from).get(possibleWaypoint) < connections.get(from).get(to)) {
                long distance = Math.min(connections.get(from).get(possibleWaypoint) +
                                         getMinDistance(possibleWaypoint, to),
                                         disconnected);
                if (connections.get(from).containsKey(to)) {
                    if (distance < connections.get(from).get(to)) {
                        connections.get(from).put(to, distance);
                    }
                } else {
                    connections.get(from).put(to, distance);
                }
            }
        }

        return connections.get(from).get(to);
    }

    public long sumOfDistances(int N, int S, int[] params) {
        graphGen(N, params);
        long retval = 0;
        for (int i = 0; i < N; i++) {
            if (i != S) {
                retval += getMinDistance(S, i);
            }
        }
        return retval;
    }
}