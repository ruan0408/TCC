package com.smartsampa.busapi;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 12/03/2016.
 */
public class Utils {

    protected static  <T, K> List<T> convertKArrayToTList(K[] array, Function<K, T> function) {
        List<K> list = Arrays.asList(array);
        return convertKListToT(list, function);
    }

    protected static  <T, K> List<T> convertKListToT(Collection<K> collection, Function<K, T> function) {
        return collection.stream()
                .map(function)
                .collect(Collectors.toList());
    }
}
