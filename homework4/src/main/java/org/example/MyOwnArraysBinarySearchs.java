package org.example;

import java.util.Arrays;
import java.util.Comparator;

public class MyOwnArraysBinarySearchs {

    /***
     * Searches the specified array of bytes for the specified value using the binary search algorithm.
     * The array must be sorted (as by the sort(byte[]) method) prior to making this call.
     * If it is not sorted, the results are undefined. If the array contains multiple elements with the specified value,
     * there is no guarantee which one will be found.
     *
     * @Returns:
     * index of the search key, if it is contained in the array; otherwise, (-(insertion point) - 1).
     * The insertion point is defined as the point at which the key would be inserted into the array:
     * the index of the first element greater than the key, or a.length if all elements in the array are less than
     * the specified key. Note that this guarantees that the return value will be >= 0 if and only if the key is found.
     *
     */

    public static int binarySearch(byte[] a, byte key) {
        return privateBinarySearch(a, 0, a.length, key);
    }


    /***
     * Throws:
     * IllegalArgumentException - if fromIndex > toIndex
     * ArrayIndexOutOfBoundsException - if fromIndex < 0 or toIndex > a.length
     */

    public static int binarySearch(byte[] a, int fromIndex, int toIndex, byte key) {
        checkIndexes(a.length, fromIndex, toIndex);
        return privateBinarySearch(a, fromIndex, toIndex, key);
    }

    private static int privateBinarySearch(byte[] a, int fromIndex, int toIndex, byte key) {
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1; // Середина массива
            byte midVal = a[mid];
            if (midVal < key)
                low = mid + 1; // Ключ в правой половине
            else if (midVal > key)
                high = mid - 1; // Ключ в левой половине
            else
                return mid; // Ключ найден
        }
        return -(low + 1); // Ключ не найден
    }

    private static void checkIndexes(int a, int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException();
        }
        if (fromIndex < 0 || toIndex > a) {
            throw new ArrayIndexOutOfBoundsException(fromIndex);
        }

    }


    public static int binarySearch(char[] a, char key) {
        return privateBinarySearch(a, 0, a.length, key);
    }

    private static int privateBinarySearch(char[] a, int fromIndex, int toIndex, char key) {
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1; // Середина массива
            char midVal = a[mid];
            if (midVal < key)
                low = mid + 1; // Ключ в правой половине
            else if (midVal > key)
                high = mid - 1; // Ключ в левой половине
            else
                return mid; // Ключ найден
        }
        return -(low + 1); // Ключ не найден
    }

    public static int binarySearch(char[] a, int fromIndex, int toIndex, char key) {
        checkIndexes(a.length, fromIndex, toIndex);
        return privateBinarySearch(a, fromIndex, toIndex, key);
    }

    public static int binarySearch(double[] a, double key) {
        return privateBinarySearch(a, 0, a.length, key);
    }

    public static int binarySearch(double[] a, int fromIndex, int toIndex, double key) {
        checkIndexes(a.length, fromIndex, toIndex);
        return privateBinarySearch(a, fromIndex, toIndex, key);
    }

    private static int privateBinarySearch(double[] a, int fromIndex, int toIndex, double key) {
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1; // Середина массива
            double midVal = a[mid];
            if (midVal < key)
                low = mid + 1; // Ключ в правой половине
            else if (midVal > key)
                high = mid - 1; // Ключ в левой половине
            else
                return mid; // Ключ найден
        }
        return -(low + 1); // Ключ не найден
    }

    public static int binarySearch(float[] a, float key) {
        return privateBinarySearch(a, 0, a.length, key);
    }

    private static int privateBinarySearch(float[] a, int fromIndex, int toIndex, float key) {
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1; // Середина массива
            float midVal = a[mid];
            if (midVal < key)
                low = mid + 1; // Ключ в правой половине
            else if (midVal > key)
                high = mid - 1; // Ключ в левой половине
            else
                return mid; // Ключ найден
        }
        return -(low + 1); // Ключ не найден
    }

    public static int binarySearch(float[] a, int fromIndex, int toIndex, float key) {
        checkIndexes(a.length, fromIndex, toIndex);
        return privateBinarySearch(a, fromIndex, toIndex, key);
    }

    public static int binarySearch(int[] a, int key) {
        return privateBinarySearch(a, 0, a.length, key);
    }

    public static int binarySearch(int[] a, int fromIndex, int toIndex, int key) {
        checkIndexes(a.length, fromIndex, toIndex);
        return privateBinarySearch(a, fromIndex, toIndex, key);
    }

    private static int privateBinarySearch(int[] a, int fromIndex, int toIndex, int key) {
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1; // Середина массива
            int midVal = a[mid];
            if (midVal < key)
                low = mid + 1; // Ключ в правой половине
            else if (midVal > key)
                high = mid - 1; // Ключ в левой половине
            else
                return mid; // Ключ найден
        }
        return -(low + 1); // Ключ не найден
    }


    public static int binarySearch(long[] a, long key) {
        return privateBinarySearch(a, 0, a.length, key);
    }

    public static int binarySearch(long[] a, int fromIndex, int toIndex, long key) {
        checkIndexes(a.length, fromIndex, toIndex);
        return privateBinarySearch(a, fromIndex, toIndex, key);
    }

    private static int privateBinarySearch(long[] a, int fromIndex, int toIndex, long key) {
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1; // Середина массива
            long midVal = a[mid];
            if (midVal < key)
                low = mid + 1; // Ключ в правой половине
            else if (midVal > key)
                high = mid - 1; // Ключ в левой половине
            else
                return mid; // Ключ найден
        }
        return -(low + 1); // Ключ не найден
    }

    public static int binarySearch(short[] a, short key) {
        return privateBinarySearch(a, 0, a.length, key);
    }

    public static int binarySearch(short[] a, int fromIndex, int toIndex, short key) {
        checkIndexes(a.length, fromIndex, toIndex);
        return privateBinarySearch(a, fromIndex, toIndex, key);
    }

    private static int privateBinarySearch(short[] a, int fromIndex, int toIndex, short key) {
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1; // Середина массива
            short midVal = a[mid];
            if (midVal < key)
                low = mid + 1; // Ключ в правой половине
            else if (midVal > key)
                high = mid - 1; // Ключ в левой половине
            else
                return mid; // Ключ найден
        }
        return -(low + 1); // Ключ не найден
    }


    /**
     * Parameters:
     * a - the array to be searched
     * key - the value to be searched for
     * c - the comparator by which the array is ordered. A null value indicates that the elements'
     * natural ordering should be used.
     * Returns:
     * index of the search key, if it is contained in the array; otherwise, (-(insertion point) - 1).
     * The insertion point is defined as the point at which the key would be inserted into the array:
     * the index of the first element greater than the key, or a.length if all elements in the array are less
     * than the specified key. Note that this guarantees that the return value will be >= 0 if and only if the key is found.
     * Throws:
     * ClassCastException - if the array contains elements that are not mutually comparable using the specified comparator,
     * or the search key is not comparable to the elements of the array using this comparator.
     */

    public static <T> int binarySearch(T[] a, T key, Comparator c) {
        if (c == null) return privateBinarySearch(a, 0, a.length, key);
        return privateBinarySearch(a, 0, a.length, key, c);
    }

    public static <T> int binarySearch(T[] a, int fromIndex, int toIndex, T key, Comparator c) {
        checkIndexes(a.length, fromIndex, toIndex);
        if (c == null) return privateBinarySearch(a, fromIndex, toIndex, key, c);
        return privateBinarySearch(a, fromIndex, toIndex, key, c);
    }

    private static <T> int privateBinarySearch(T[] a, int fromIndex, int toIndex, T key, Comparator c) {
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1; // Середина массива
            T midVal = a[mid];
            int result = c.compare(midVal, key);
            if (result < 0)
                low = mid + 1; // Ключ в правой половине
            else if (result > 0)
                high = mid - 1; // Ключ в левой половине
            else
                return mid; // Ключ найден
        }
        return -(low + 1); // Ключ не найден
    }

    private static <T> int privateBinarySearch(T[] a, int fromIndex, int toIndex, T key) {
        int low = fromIndex;
        int high = toIndex - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1; // Середина массива
            Comparable midVal = (Comparable) a[mid];
            int result = midVal.compareTo((Comparable) key);
            if (result < 0)
                low = mid + 1; // Ключ в правой половине
            else if (result > 0)
                high = mid - 1; // Ключ в левой половине
            else
                return mid; // Ключ найден
        }
        return -(low + 1); // Ключ не найден
    }

}
