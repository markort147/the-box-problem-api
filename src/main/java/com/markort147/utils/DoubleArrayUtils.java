package com.markort147.utils;

/**
 * Utility class providing methods for operations on two-dimensional arrays.
 */
public class DoubleArrayUtils {

    private DoubleArrayUtils() {
    }

    /**
     * Retrieves the last element from a two-dimensional array.
     *
     * @param doubleArray The two-dimensional array from which to retrieve the element.
     * @return The last element in the two-dimensional array.
     */
    public static Object getLastElement(Object[][] doubleArray) {
        return doubleArray[doubleArray.length - 1][doubleArray[doubleArray.length - 1].length - 1];
    }
}
