package com.smartsampa.busapi;

import com.smartsampa.olhovivoapi.BusCorridor;
import com.smartsampa.olhovivoapi.BusStop;

import java.util.List;

import static com.smartsampa.busapi.BusAPIManager.olhovivo;

/**
 * Created by ruan0408 on 13/03/2016.
 */
public class CorridorFacade {

    protected CorridorFacade(){}

    protected static List<Corridor> getAllCorridors() {
        BusCorridor[] allBusCorridors = olhovivo.getAllBusCorridors();
        return DataToAPIConverter.busCorridorArrayToCorridors(allBusCorridors);
    }

    protected List<Stop> getAllStopsFromCorridor(int corridorId) {
        BusStop[] busStops = olhovivo.searchBusStopsByCorridor(corridorId);
        return DataToAPIConverter.busStopArrayToStops(busStops);
    }
}
