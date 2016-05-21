package com.smartsampa.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ruan0408 on 20/02/2016.
 */
public class Utils {

    public static <T> void printCollection(Collection<T> list) {
        for (T t : list)
            System.out.println(t.toString());
    }

    public static <T> Map<T, T> mirroredMap(Set<T> set) {
        Map<T, T> map = new HashMap<>(set.size());
        for (T t : set)
            map.put(t, t);

        return map;
    }

}
