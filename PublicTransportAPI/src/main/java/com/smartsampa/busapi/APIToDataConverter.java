package com.smartsampa.busapi;

import com.smartsampa.gtfsapi.GtfsAPI;
import com.smartsampa.olhovivoapi.BusLine;
import com.smartsampa.olhovivoapi.OlhoVivoAPI;
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
