package com.intellij.publictransportapi;

import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.StopTime;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ruan0408 on 20/02/2016.
 */
public class Utils {

    protected static Comparator<StopTime> compByStopSequence = (s1, s2) -> {
        if (s1.getStopSequence() < s2.getStopSequence()) return -1;
        if (s1.getStopSequence() > s2.getStopSequence()) return 1;
        return 0;
    };

    protected static Comparator<ShapePoint> compBySequence = (p1, p2) -> {
        if (p1.getSequence() < p2.getSequence()) return -1;
        if (p1.getSequence() > p2.getSequence()) return 1;
        return 0;
    };

    protected static <T> void printList(List<T> list) {
        for (T t : list) System.out.println(t.toString());
    }

    protected static
    List<org.onebusaway.gtfs.model.Stop> convert(List<StopTime> stopTimes) {
        List<org.onebusaway.gtfs.model.Stop> list =
                new ArrayList<>(stopTimes.size());

        for (StopTime stopTime : stopTimes)
            list.add(stopTime.getStop());

        return list;
    }
}
