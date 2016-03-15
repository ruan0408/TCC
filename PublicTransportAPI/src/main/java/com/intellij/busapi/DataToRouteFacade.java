package com.intellij.busapi;

import com.intellij.olhovivoapi.BusLine;
import com.intellij.openapi.util.Pair;

/**
 * Created by ruan0408 on 13/03/2016.
 */
public class DataToRouteFacade extends BusAPIUser {


    public double getFarePrice(String fullNumberSign) {
        return gtfsAPI.getFarePrice(fullNumberSign);
    }

    protected static Route buildFrom(String fullNumberSign, String heading, Trip trip) {
        Route newRoute = new Route();
        Pair<BusLine, BusLine> bothTrips = olhoVivoAPI.getBothTrips(fullNumberSign);

        newRoute.setNumberSign(fullNumberSign.substring(0,4));
        newRoute.setType(Integer.parseInt(fullNumberSign.substring(5)));
        newRoute.setCircular(bothTrips.first.isCircular());
        newRoute.setInfo(bothTrips.first.getInfo());

        if (heading.equalsIgnoreCase("mtst")) {
            trip.setDestinationSign(bothTrips.first.getDestinationSignMTST());
            newRoute.setMTST(trip);
            Trip stmt = new Trip(fullNumberSign, "stmt", newRoute.isCircular());
            stmt.setDestinationSign(bothTrips.second.getDestinationSignSTMT());
            stmt.setRoute(newRoute);
        } else {
            trip.setDestinationSign(bothTrips.second.getDestinationSignSTMT());
            newRoute.setSTMT(trip);
            Trip mtst = new Trip(fullNumberSign, "mtst", newRoute.isCircular());
            mtst.setDestinationSign(bothTrips.first.getDestinationSignMTST());
            mtst.setRoute(newRoute);
        }

        return newRoute;
    }
}
