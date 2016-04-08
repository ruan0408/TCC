package com.smartsampa.busapi;

import com.smartsampa.olhovivoapi.BusLine;
import com.intellij.openapi.util.Pair;

import static com.smartsampa.busapi.BusAPIManager.gtfs;
import static com.smartsampa.busapi.BusAPIManager.olhovivo;

/**
 * Created by ruan0408 on 13/03/2016.
 */
public class RouteFacade {


    public double getFarePrice(String fullNumberSign) {
        return gtfs.getFarePrice(fullNumberSign);
    }

    protected static Route buildFrom(String fullNumberSign) {
        Route newRoute = new Route();
        Pair<BusLine, BusLine> bothTrips = olhovivo.getBothTrips(fullNumberSign);

        newRoute.setNumberSign(fullNumberSign.substring(0,4));
        newRoute.setType(Integer.parseInt(fullNumberSign.substring(5)));
        newRoute.setCircular(bothTrips.first.isCircular());
        newRoute.setInfo(bothTrips.first.getInfo());

        Trip mtst = new Trip();
        mtst.setDestinationSign(bothTrips.first.getDestinationSignMTST());
        mtst.setRoute(newRoute);

        Trip stmt = new Trip();
        stmt.setDestinationSign(bothTrips.second.getDestinationSignSTMT());
        stmt.setRoute(newRoute);

        newRoute.setMTST(mtst);
        newRoute.setSTMT(stmt);

        return newRoute;
    }
}
