package org.salwasser.structures;

import java.util.ArrayList;

/**
 * Created by Zac on 6/14/2016.
 */
public class SortableArrayList<T extends Comparable> extends ArrayList<Comparable> {

    private int shiftPivot(int shift, int pivotIdx, int toSwapIdx) {
        Comparable tmp = get(toSwapIdx);

        if (toSwapIdx == pivotIdx + shift) {
            //Adjacent elements.
            set(toSwapIdx, get(pivotIdx));
        } else {
            set(toSwapIdx, get(pivotIdx + shift));
            set(pivotIdx + shift, get(pivotIdx));
        }

        set(pivotIdx, tmp);
        return pivotIdx + shift;
    }

    private int shiftPivotLeft(int pivotIdx, int toSwapIdx) {
        return shiftPivot(-1, pivotIdx, toSwapIdx);
    }

    private int shiftPivotRight(int pivotIdx, int toSwapIdx) {
        return shiftPivot(1, pivotIdx, toSwapIdx);
    }

    private void quickSort(int lhand, int rhand, int logicProduct) {
        if (lhand == rhand) {
            return;
        }

        if (rhand == lhand + 1) {
            if (get(lhand).compareTo(get(rhand)) * logicProduct > 0) {
                Comparable tmp = get(lhand);
                set(lhand, get(rhand));
                set(rhand, tmp);
            }
            return;
        }

        int pivotReader = ((rhand - lhand) / 2) + lhand;
        Comparable pivot = get(pivotReader);

        int lhandReader = lhand;
        int rhandReader = pivotReader + 1;

        while (lhandReader < pivotReader ||
               rhandReader < rhand) {
            boolean lSwapNeeded = lhandReader < pivotReader &&
                                  pivot.compareTo(get(lhandReader)) < 0;
            boolean rSwapNeeded = rhandReader < rhand &&
                                  pivot.compareTo(get(rhandReader)) > 0;
            boolean incrementLhand = true;

            if (lSwapNeeded && !rSwapNeeded) {
                pivotReader = shiftPivotLeft(pivotReader, lhandReader);
                incrementLhand = false;
            } else if (rSwapNeeded && !lSwapNeeded) {
                pivotReader = shiftPivotRight(pivotReader, rhandReader);
            } else if (lSwapNeeded && rSwapNeeded) {
                Comparable tmp = get(lhandReader);
                set(lhandReader, get(rhandReader));
                set(rhandReader, tmp);
            }

            if (incrementLhand && lhandReader < pivotReader) {
                lhandReader++;
            }

            if (rhandReader < rhand) {
                rhandReader++;
            }
        }

        quickSort(lhand, pivotReader, logicProduct);
        quickSort(pivotReader + 1, rhand, logicProduct);
    }

    public void quickSort(boolean descending) {
        quickSort(0, size(), descending?-1:1);
    }
    public void quickSort() {
        quickSort(false);
    }
}
