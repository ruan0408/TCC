package com.intellij.busapi;

import com.intellij.olhovivoapi.BusCorridor;
import com.intellij.olhovivoapi.BusStop;

import java.util.List;

import static com.intellij.busapi.BusAPIManager.olhovivo;

/**
 * Created by ruan0408 on 13/03/2016.
 */
public class DataToCorridorFacade  {

    protected DataToCorridorFacade(){}

    protected static List<Corridor> getAllCorridors() {
        BusCorridor[] allBusCorridors = olhovivo.getAllBusCorridors();
        return DataToAPIConverter.busCorridorArrayToCorridors(allBusCorridors);
    }

    protected List<Stop> getAllStopsFromCorridor(int corridorId) {
        BusStop[] busStops = olhovivo.searchBusStopsByCorridor(corridorId);
        return DataToAPIConverter.busStopArrayToStops(busStops);
    }
}
