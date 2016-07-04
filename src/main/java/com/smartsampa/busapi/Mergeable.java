package com.smartsampa.busapi;

import com.smartsampa.utils.Utils;

import java.util.Map;
import java.util.Set;

/**
 * Created by ruan0408 on 30/04/2016.
 */
public interface Mergeable {

    static <T extends Mergeable>  Set<T> mergeSets(Set<T> stops1, Set<T> stops2) {
        Map<T,T> otherStops = Utils.mirroredMap(stops2);

        stops1.stream().map(stop -> {
            Mergeable other = otherStops.get(stop);
            stop.merge(other);
            return other;
        }).forEach(otherStops::remove);

        stops1.addAll(otherStops.keySet());
        return stops1;
    }

    void merge(Mergeable m);
}
