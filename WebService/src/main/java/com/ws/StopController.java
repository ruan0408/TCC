package com.ws;

import com.fasterxml.jackson.annotation.JsonView;
import com.smartsampa.busapi.BusAPI;
import com.smartsampa.busapi.PredictedBus;
import com.smartsampa.busapi.Stop;
import com.smartsampa.busapi.Trip;
import com.ws.View.TripSummary;
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

    @RequestMapping("/search")
    public Set<Stop> getTripsSummaryByTerm(@RequestParam(value="term", defaultValue="") String searchTerm) {
        return BusAPI.getStopsByTerm(searchTerm);
    }

    @RequestMapping("/{stopId}")
    public Stop getCompleteTripById(@PathVariable(value = "stopId") int stopId) {
        return BusAPI.getStopById(stopId);
    }

    @JsonView(TripSummary.class)
    @RequestMapping("/{stopId}/trips")
    public Set<Trip> getTrips(@PathVariable(value = "stopId") int stopId) {
        Stop stop = BusAPI.getStopById(stopId);
        return BusAPI.getTripsFromStop(stop);
    }

    @RequestMapping("/{stopId}/predictions/trips")
    public Map<Trip, List<PredictedBus>> getTripPredictions(@PathVariable(value = "stopId") int stopId) {
        Stop stop = BusAPI.getStopById(stopId);
        return BusAPI.getPredictionsPerTrip(stop);
    }

    @RequestMapping("/{stopId}/predictions/trips/{tripId}")
    public List<PredictedBus> getTripPredictionsAtStop(@PathVariable(value = "stopId") int stopId,
                                                       @PathVariable(value = "tripId") String tripId) {
        Stop stop = BusAPI.getStopById(stopId);
        Trip trip = BusAPI.getTripById(tripId);
        return BusAPI.getPredictions(trip, stop);
    }
}
