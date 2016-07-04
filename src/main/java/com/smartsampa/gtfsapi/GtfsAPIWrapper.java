package com.smartsampa.gtfsapi;

import com.smartsampa.busapi.Stop;
import com.smartsampa.busapi.Trip;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by ruan0408 on 3/07/2016.
 */
public class GtfsAPIWrapper {

    private GtfsAPI gtfsAPI;

    public GtfsAPIWrapper(GtfsAPI gtfsAPI) {
        this.gtfsAPI = gtfsAPI;
    }

    public Set<Trip> getTripsByTerm(String term) {
        return gtfsAPI.getTripsWithRouteContaining(term).stream()
                .map(GtfsTrip::new)
                .flatMap(trip -> Stream.of(trip, trip.cloneChangingHeadingAndDestinationSign()))
                .filter(trip -> trip.containsTerm(term))
                .collect(Collectors.toSet());
    }

    public Set<Trip> getTripsFromStop(Stop stop) {
        return gtfsAPI.getAllTripsFromStopId(stop.getId()).stream()
                .map(GtfsTrip::new)
                .collect(toSet());
    }

    public Set<Stop> getStopsByTerm(String term) {
        return gtfsAPI.getStopsByTerm(term).stream()
                .map(GtfsStop::new)
                .collect(toSet());
    }

    public List<Stop> getStopsFromTrip(Trip trip) {
        return gtfsAPI.getAllStopsOrderedFromTripId(trip.getGtfsId())
                .stream()
                .map(GtfsStop::new)
                .collect(toList());
    }

    public Stop getStopById(int id) {
        return new GtfsStop(gtfsAPI.getStopById(id));
    }


}
