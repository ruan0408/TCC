package com.smartsampa.busapi.impl;

import com.smartsampa.busapi.model.AbstractStop;
import com.smartsampa.busapi.model.Stop;
import com.smartsampa.busapi.model.Trip;
import com.smartsampa.utils.Point;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public class GtfsStop extends AbstractStop {

    private org.onebusaway.gtfs.model.Stop gtfsStop;

    public GtfsStop(org.onebusaway.gtfs.model.Stop gtfsStop) {
        this.gtfsStop = gtfsStop;
    }

    static Stop getStopById(int id) {
        org.onebusaway.gtfs.model.Stop stop = BusAPI.gtfs.getStopById(id);
        return new GtfsStop(stop);
    }

    public static Set<Stop> getStopsByTerm(String term) {
        return BusAPI.gtfs.getStopsByTerm(term).stream()
                .map(GtfsStop::new)
                .collect(toSet());
    }

    @Override
    public Integer getId() {
        return Integer.parseInt(gtfsStop.getId().getId());
    }

    @Override
    public String getName() {
        return gtfsStop.getName();
    }

    @Override
    public String getReference() {return gtfsStop.getDesc();}

    @Override
    public Point getLocation() {
        return new Point(gtfsStop.getLat(), gtfsStop.getLon());
    }

    //TODO make this return complete trips and make it efficient
    @Override
    public Set<Trip> getTrips() {
        return BusAPI.gtfs.getAllTripsFromStopId(getId()).stream()
                .map(GtfsTrip::new)
                .map(trip -> BusAPI.getTrip(trip.getNumberSign(), trip.getHeading()))
                .collect(Collectors.toSet());
    }
}
