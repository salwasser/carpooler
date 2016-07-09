package org.salwasser.structures;

/**
 * Created by Zac on 7/8/2016.
 */
public class FuzzyString {
    private String value;

    public FuzzyString(String _value) {
        value = _value;
    }

    public int distanceTo(String other) {
        if (other.length() == 0) {
            return value.length();
        }

        if (value.length() == 0) {
            return other.length();
        }

        if (value.equals(other)) {
            return 0;
        }

        int[][] matrix = new int[value.length() + 1][other.length() + 1];
        for (int y = 0; y < value.length() + 1; y++) {
            matrix[y][0] = y;
        }
        for (int x = 0; x < other.length() + 1; x++) {
            matrix[0][x] = x;
        }

        int valueBeginIndex = 0;
        for (int y = 1; y < value.length() + 1; y++) {
            int otherBeginIndex = 0;
            for (int x = 1; x < other.length() + 1; x++) {
                int subCost = matrix[y - 1][x - 1] + ((other.substring(otherBeginIndex, x).equals(value.substring(valueBeginIndex, y)))?0:1);
                int delCost = matrix[y][x - 1] + 1;
                int insCost = matrix[y - 1][x] + 1;
                matrix[y][x] = Math.min(Math.min(subCost, delCost), insCost);
                otherBeginIndex = x;
            }
            valueBeginIndex = y;
        }

        return matrix[value.length()][other.length()];

    }

}
