package com.intellij.busapi;

import com.intellij.gtfsapi.GtfsAPI;
import com.intellij.olhovivoapi.BusLine;
import com.intellij.olhovivoapi.OlhoVivoAPI;
import com.intellij.openapi.util.Pair;

/**
 * Created by ruan0408 on 12/03/2016.
 */
public class APIToDataConverter {

    public String getGtfsIdFromTrip(String fullNumberSign, String heading, boolean isCircular) {
        if (isCircular || heading.equalsIgnoreCase("stmt"))
            return fullNumberSign+"-"+0;

        return fullNumberSign+"-"+1;
    }

    public String getTripShapeIdFromGtfsId(String gtfsId) {
        return getGtfs().getTripById(gtfsId).getShapeId().getId();
    }

    public int getOlhovivoTripId(String fullNumberSign, String heading) {
        Pair<BusLine, BusLine> bothTrips = getOlhovivo().getBothTrips(fullNumberSign);
        if (heading.equalsIgnoreCase("mtst"))
            return bothTrips.getFirst().getCode();

        return bothTrips.getSecond().getCode();
    }

    private static OlhoVivoAPI getOlhovivo() {
        return BusAPIManager.olhovivo;
    }

    private static GtfsAPI getGtfs() {
        return BusAPIManager.gtfs;
    }
}
