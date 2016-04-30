package com.smartsampa.busapi;

import com.smartsampa.olhovivoapi.BusLine;
import org.apache.commons.lang3.tuple.Pair;

import static com.smartsampa.busapi2.impl.BusAPIManager.gtfs;
import static com.smartsampa.busapi2.impl.BusAPIManager.olhovivo;

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
        return gtfs.getTripById(gtfsId).getShapeId().getId();
    }

    public int getOlhovivoTripId(String fullNumberSign, String heading) {
        Pair<BusLine, BusLine> bothTrips = olhovivo.getBothTrips(fullNumberSign);
        if (heading.equalsIgnoreCase("mtst"))
            return bothTrips.getLeft().getOlhovivoId();

        return bothTrips.getRight().getOlhovivoId();
    }

}
