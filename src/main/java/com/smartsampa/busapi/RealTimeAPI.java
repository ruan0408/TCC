package com.smartsampa.busapi;

import com.smartsampa.olhovivoapi.OlhovivoAPI;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ruan0408 on 15/07/2016.
 */
public class RealTimeAPI {

    private OlhovivoAPI olhovivoAPI;
    private DataFiller dataFiller;

    public RealTimeAPI() {
        olhovivoAPI = Provider.getOlhovivoAPI();
        dataFiller = new DataFiller();
    }

    //TODO test that the trips are complete
    public Map<Trip, List<PredictedBus>> getPredictionsPerTrip(Stop stop) {
        Map<Trip, List<PredictedBus>> predictions = olhovivoAPI.getPredictionsAtStop(stop.getId());
        predictions.keySet().stream().forEach(dataFiller::fill);
        return predictions;
    }

    public Map<Stop, List<PredictedBus>> getPredictionsPerStop(Trip trip) {
        Map<Stop, List<PredictedBus>> predictions = olhovivoAPI.getPredictionsOfTrip(trip.getOlhovivoId());
        predictions.keySet().stream().forEach(dataFiller::fill);
        return predictions;
    }

    public List<PredictedBus> getPredictions(Trip trip, Stop stop) {
        return olhovivoAPI.getPredictionsOfTripAtStop(trip.getOlhovivoId(), stop.getId());
    }

    public Set<Bus> getAllRunningBuses(Trip trip) {
        return olhovivoAPI.getAllRunningBusesOfTrip(trip.getOlhovivoId());
    }
}
