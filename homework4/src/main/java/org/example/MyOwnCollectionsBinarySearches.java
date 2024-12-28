package org.example;

import java.util.Comparator;
import java.util.List;

public class MyOwnCollectionsBinarySearches {

    /***
     * Returns:
     * the index of the search key, if it is contained in the list; otherwise, (-(insertion point) - 1).
     * The insertion point is defined as the point at which the key would be inserted into the list:
     * the index of the first element greater than the key, or list.size() if all elements in the list are less
     * than the specified key. Note that this guarantees that the return value will be >= 0 if and only if the key is found.
     * Throws:
     * ClassCastException - if the list contains elements that are not mutually comparable
     * (for example, strings and integers), or the search key is not mutually comparable with the elements of the list.
     *
     */

    public static <T> int binarySearch(List<? extends Comparable<? super T>> list, T key) {
        int low = 0;
        int high = list.size() - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1; // Середина списка
            Comparable<? super T> midVal = list.get(mid);
            int cmp = midVal.compareTo(key);
            if (cmp < 0)
                low = mid + 1; // Ключ в правой половине
            else if (cmp > 0)
                high = mid - 1; // Ключ в левой половине
            else
                return mid; // Ключ найден
        }
        return -(low + 1); // Ключ не найден
    }


    public static <T> int binarySearch(List<? extends T> list, T key, Comparator<? super T> c) {
        int low = 0;
        int high = list.size() - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1; // Середина списка
            T midVal = list.get(mid);
            int cmp = c.compare(midVal, key);
            if (cmp < 0)
                low = mid + 1; // Ключ в правой половине
            else if (cmp > 0)
                high = mid - 1; // Ключ в левой половине
            else
                return mid; // Ключ найден
        }
        return -(low + 1); // Ключ не найден
    }


}
