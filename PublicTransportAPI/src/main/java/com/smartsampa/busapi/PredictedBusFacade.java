package com.smartsampa.busapi;

import com.smartsampa.olhovivoapi.BusNow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruan0408 on 9/04/2016.
 */
public class PredictedBusFacade extends BusFacade {

    protected static List<PredictedBus> busNowArrayToPredictedBuses(BusNow[] buses) {
        if (buses == null) return new ArrayList<>();

        List<PredictedBus> predictedBuses = convertAnyOlhovivoBusToAnyAPIBus(buses, PredictedBus.class);

        for (int i = 0; i < buses.length; i++)
            predictedBuses.get(i).setPredictedArrival(buses[i].getPredictedArrivalTime());

        return predictedBuses;
    }
}
