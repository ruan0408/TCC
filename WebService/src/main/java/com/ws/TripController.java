package com.ws;

import com.fasterxml.jackson.annotation.JsonView;
import com.smartsampa.busapi.impl.BusAPI;
import com.smartsampa.busapi.model.Bus;
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
 * Created by ruan0408 on 9/04/2016.
 */
@RestController
@RequestMapping("/trips")
public class TripController {

    static {
//        BusAPI.setSptransLogin("ruan0408");
//        BusAPI.setSptransPassword("costaruan");
//        BusAPI.setOlhovivoKey("3de5ce998806e0c0750b1434e17454b6490ccf0a595f3884795da34460a7e7b3");
//        BusAPI.initialize();
    }

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

    @RequestMapping("/{tripId}/predictions/buses")
    public Set<Bus> getAllRunningBuses(@PathVariable(value = "tripId") String tripId) {
        return BusAPI.getTripById(tripId).getAllRunningBuses();
    }

    @RequestMapping("/{tripId}/predictions/stops")
    public Map<Stop, List<PredictedBus>> getPredictionsPerStop(@PathVariable(value = "tripId") String tripId) {
        return BusAPI.getTripById(tripId).getPredictionsPerStop();
    }

    @RequestMapping("/{tripId}/predictions/stops/{stopId}")
    public List<PredictedBus> getTripPredictionsAtStop(@PathVariable(value = "tripId") String tripId,
                                                       @PathVariable(value = "stopId") int stopId) {
        Stop stop = BusAPI.getStopById(stopId);
        Trip trip = BusAPI.getTripById(tripId);
        return trip.getPredictionsAtStop(stop);
    }
}
