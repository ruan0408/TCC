package com.smartsampa.utils;

import java.util.Collection;

/**
 * Created by ruan0408 on 20/02/2016.
 */
public class Utils {

    public static <T> void printList(Collection<T> list) {
        for (T t : list)
            System.out.println(t.toString());
    }

}
