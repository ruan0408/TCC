package com.smartsampa.busapi;

import com.smartsampa.olhovivoapi.BusCorridor;
import com.smartsampa.olhovivoapi.BusStop;

import java.util.ArrayList;
import java.util.List;

import static com.smartsampa.busapi2.impl.BusAPIManager.olhovivo;

/**
 * Created by ruan0408 on 13/03/2016.
 */
public class CorridorFacade {

    protected CorridorFacade(){}

    protected static List<Corridor> getAllCorridors() {
        BusCorridor[] allBusCorridors = olhovivo.getAllBusCorridors();
        return busCorridorArrayToCorridorsList(allBusCorridors);
    }

    protected List<Stop> getAllStopsFromCorridor(int corridorId) {
        BusStop[] busStops = olhovivo.searchBusStopsByCorridor(corridorId);
        return StopFacade.busStopArrayToStops(busStops);
    }

    private static List<Corridor> busCorridorArrayToCorridorsList(BusCorridor[] corridors) {
        if (corridors == null) return new ArrayList<>();

        return Utils.convertKArrayToTList(corridors, corridor -> busCorridorToCorridor(corridor));
    }

    private static Corridor busCorridorToCorridor(BusCorridor corridor) {
        return new Corridor(corridor.getId(), corridor.getName());
    }
}
