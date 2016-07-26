package com.ws;

import com.fasterxml.jackson.annotation.JsonView;
import com.smartsampa.busapi.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ruan0408 on 9/04/2016.
 */
@RestController
@RequestMapping("/trips")
public class TripController {

    @JsonView(View.TripSummary.class)
    @RequestMapping("/search")
    public Set<Trip> getTripsSummaryByTerm(@RequestParam(value="term", defaultValue="") String searchTerm) {
        return BusAPI.getTripsByTerm(searchTerm);
    }

    @JsonView(View.TripComplete.class)
    @RequestMapping("/{tripId}")
    public Trip getCompleteTripById(@PathVariable(value = "tripId") String tripId) {
        return BusAPI.getTripById(tripId);
    }

    @RequestMapping("/{tripId}/stops")
    public List<Stop> getStops(@PathVariable(value = "tripId") String tripId) {
        Trip trip = BusAPI.getTripById(tripId);
        return BusAPI.getStopsFromTrip(trip);
    }

    @RequestMapping("/{tripId}/predictions/buses")
    public Set<Bus> getAllRunningBuses(@PathVariable(value = "tripId") String tripId) {
        Trip trip = BusAPI.getTripById(tripId);
        return BusAPI.getAllRunningBuses(trip);
    }

    @RequestMapping("/{tripId}/predictions/stops")
    public Map<Stop, List<PredictedBus>> getPredictionsPerStop(@PathVariable(value = "tripId") String tripId) {
        Trip trip = BusAPI.getTripById(tripId);
        return BusAPI.getPredictionsPerStop(trip);
    }

    @RequestMapping("/{tripId}/predictions/stops/{stopId}")
    public List<PredictedBus> getTripPredictionsAtStop(@PathVariable(value = "tripId") String tripId,
                                                       @PathVariable(value = "stopId") int stopId) {
        Trip trip = BusAPI.getTripById(tripId);
        Stop stop = BusAPI.getStopById(stopId);
        return BusAPI.getPredictions(trip, stop);
    }
}
