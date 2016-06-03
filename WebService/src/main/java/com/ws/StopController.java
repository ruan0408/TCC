package com.ws;

import com.fasterxml.jackson.annotation.JsonView;
import com.smartsampa.busapi.impl.BusAPI;
import com.smartsampa.busapi.model.PredictedBus;
import com.smartsampa.busapi.model.Stop;
import com.smartsampa.busapi.model.Trip;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ruan0408 on 27/05/2016.
 */
@RestController
@RequestMapping("/stops")
public class StopController {

    @JsonView(View.StopSummary.class)
    @RequestMapping("/search")
    public Set<Stop> getTripsSummaryByTerm(@RequestParam(value="term", defaultValue="") String searchTerm) {
        return BusAPI.getStopsByTerm(searchTerm);
    }

    @JsonView(View.StopComplete.class)
    @RequestMapping("/{stopId}")
    public Stop getCompleteTripById(@PathVariable(value = "stopId") int stopId) {
        return BusAPI.getStopById(stopId);
    }

    @RequestMapping("/{stopId}/predictions/trips")
    public Map<Trip, List<PredictedBus>> getTripPredictions(@PathVariable(value = "stopId") int stopId) {
        return BusAPI.getStopById(stopId).getPredictionsPerTrip();
    }

    @RequestMapping("/{stopId}/predictions/trips/{tripId}")
    public List<PredictedBus> getTripPredictionsAtStop(@PathVariable(value = "stopId") int stopId,
                                                       @PathVariable(value = "tripId") String tripId) {
        Stop stop = BusAPI.getStopById(stopId);
        Trip trip = BusAPI.getTripById(tripId);
        return stop.getPredictedBusesOfTrip(trip);
    }
}
