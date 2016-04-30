package com.smartsampa.busapi;

import com.smartsampa.olhovivoapi.BusLine;
import org.apache.commons.lang3.tuple.Pair;

import static com.smartsampa.busapi2.impl.BusAPIManager.gtfs;
import static com.smartsampa.busapi2.impl.BusAPIManager.olhovivo;

/**
 * Created by ruan0408 on 13/03/2016.
 */
public class RouteFacade {


    public double getFarePrice(String fullNumberSign) {
        return gtfs.getFarePrice(fullNumberSign);
    }

    protected static Route buildFrom(String fullNumberSign) {

        Pair<BusLine, BusLine> bothTrips = olhovivo.getBothTrips(fullNumberSign);

        Route newRoute = new Route();
        newRoute.setNumberSign(fullNumberSign.substring(0,4));
        newRoute.setType(Integer.parseInt(fullNumberSign.substring(5)));
        newRoute.setCircular(bothTrips.getLeft().isCircular());
        newRoute.setInfo(bothTrips.getRight().getInfo());

        Trip mtst = new Trip();
        mtst.setDestinationSign(bothTrips.getLeft().getDestinationSignMTST());
        mtst.setRoute(newRoute);

        Trip stmt = new Trip();
        stmt.setDestinationSign(bothTrips.getRight().getDestinationSignSTMT());
        stmt.setRoute(newRoute);

        newRoute.setMTST(mtst);
        newRoute.setSTMT(stmt);

        return newRoute;
    }
}
