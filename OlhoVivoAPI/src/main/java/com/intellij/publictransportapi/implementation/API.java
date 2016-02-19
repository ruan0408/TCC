package com.intellij.publictransportapi.implementation;

import com.intellij.olhovivoapi.BusLine;
import com.intellij.olhovivoapi.OlhoVivoAPI;
import com.intellij.openapi.util.Pair;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.serialization.GtfsReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class API {

    private static final String GTFS_PATH = "gtfs-sp";//TODO remove this
    protected static OlhoVivoAPI olhoVivoAPI;
    private static GtfsReader gtfsReader;
    protected static GtfsDaoImpl store;

    public static void init(String key) {
        olhoVivoAPI = new OlhoVivoAPI(key);
        olhoVivoAPI.authenticate();//TODO not sure about this.
        gtfsReader = new GtfsReader();

        store = new GtfsDaoImpl();
        try {
            gtfsReader.setInputLocation(new File(API.class.getClassLoader().
                    getResource(GTFS_PATH).getPath()));
            gtfsReader.setEntityStore(store);
            gtfsReader.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO refactor this method
    public static List<Trip> searchTrip(String term) {
        BusLine[] busLines = olhoVivoAPI.searchBusLines(term);
        List<Trip> trips = new ArrayList<>(busLines.length);

        for (int i = 0; i < busLines.length; i++) {
            try {
                Pair<BusLine, BusLine> pair = olhoVivoAPI.getBothTrips(busLines[i].getNumberSign());
                Trip tripMTST = new Trip();
                Trip tripSTMT = null;

                Route route = new Route(busLines[i].getNumberSign(), busLines[i].getType(),
                        busLines[i].isCircular(), busLines[i].getInfo());
                route.setSTMT(tripSTMT);

                tripMTST.setInternalId(pair.getFirst().getCode());
                tripMTST.setDestinationSign(pair.getFirst().getDestinationSignMTST());
                tripMTST.setRoute(route);

                if (pair.getSecond() != null) {
                    tripSTMT = new Trip();
                    tripSTMT.setInternalId(pair.getSecond().getCode());
                    tripSTMT.setDestinationSign(pair.getSecond().getDestinationSignSTMT());
                    tripSTMT.setRoute(route);
                }
                route.setSTMT(tripSTMT);

                if (busLines[i].getHeading() == 1) trips.add(tripMTST);
                else trips.add(tripSTMT);
            } catch (Exception e) {
                System.err.println("Error when getting both trips");
                e.printStackTrace();
            }
        }
        return trips;
    }
}
