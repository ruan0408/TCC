package com.intellij.busapi;

import com.intellij.olhovivoapi.BusCorridor;
import com.intellij.olhovivoapi.BusStop;

import java.util.List;

/**
 * Created by ruan0408 on 13/03/2016.
 */
public class DataToCorridorFacade extends BusAPIUser {

    protected DataToCorridorFacade(){}

    protected static List<Corridor> getAllCorridors() {
        BusCorridor[] allBusCorridors = olhoVivoAPI.getAllBusCorridors();
        return DataToAPIConverter.busCorridorArrayToCorridors(allBusCorridors);
    }

    protected List<Stop> getAllStopsFromCorridor(int corridorId) {
        BusStop[] busStops = olhoVivoAPI.searchBusStopsByCorridor(corridorId);
        return DataToAPIConverter.busStopArrayToStops(busStops);
    }
}
