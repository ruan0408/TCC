package com.smartsampa.gtfswrapper;

import com.smartsampa.busapi.Heading;
import com.smartsampa.busapi.Shape;
import com.smartsampa.busapi.Stop;
import com.smartsampa.busapi.Trip;
import com.smartsampa.gtfsapi.GtfsAPI;
import org.apache.commons.math3.util.Precision;
import org.onebusaway.gtfs.model.ShapePoint;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by ruan0408 on 3/07/2016.
 */
public class GtfsAPIFacade {

    private GtfsAPI gtfsAPI;

    public GtfsAPIFacade(GtfsAPI gtfsAPI) {
        this.gtfsAPI = gtfsAPI;
    }

    public Set<Trip> getTripsByTerm(String term) {
        return gtfsAPI.getTripsWithRouteContainingTerm(term).stream()
                .flatMap(trip -> Stream.of(new GtfsTripAdapter(trip), getTheOtherTrip(trip)))
                .filter(trip -> trip.containsTerm(term))
                .collect(toSet());
    }

    public Set<Stop> getStopsByTerm(String term) {
        return gtfsAPI.getStopsByTerm(term).stream()
                .map(GtfsStopAdapter::new)
                .collect(toSet());
    }

    public Set<Trip> getTripsFromStop(Stop stop) {
        return gtfsAPI.getAllTripsFromStopId(stop.getId()).stream()
                .map(GtfsTripAdapter::new)
                .collect(toSet());
    }

    public List<Stop> getStopsFromTrip(Trip trip) {
        return gtfsAPI.getAllStopsOrderedFromTripId(trip.getGtfsId()).stream()
                .map(GtfsStopAdapter::new)
                .collect(toList());
    }

    public Stop getStopById(int id) {
        return new GtfsStopAdapter(gtfsAPI.getStopById(id));
    }

    public Trip getTrip(Trip equivalentTrip) {
        return getTripsByTerm(equivalentTrip.getNumberSign()).stream()
                .filter(equivalentTrip::equals)
                .findAny()
                .orElse(Trip.emptyTrip());
    }

    //TODO this might not work
    public Stop getStop(Stop equivalentStop) {
        return getAllStops().stream()
                .filter(equivalentStop::equals)
                .findAny()
                .orElse(Stop.emptyStop());
    }

    private Set<Stop> getAllStops() {
        return getStopsByTerm("");
    }

    double getFarePrice(String numberSign) {
        double farePrice = gtfsAPI.getFarePrice(numberSign);
        return Precision.round(farePrice, 2);
    }

    Shape getShape(String shapeId) {
        List<ShapePoint> shapePoints = gtfsAPI.getShape(shapeId);
        return new GtfsShapeAdapter(shapePoints);
    }

    int getDepartureIntervalAtTime(String gtfsId, String HHmm) {
        return gtfsAPI.getDepartureIntervalAtTime(gtfsId, HHmm);
    }

    /*
    * This method creates a clone of this GtfsTripAdapter, but changes the heading
    * and the destinationSign. It's necessary because the gtfs files have only one entry
    * for circular trips, while the Olhovivo API has two entries for circular trips.
    * Therefore we "falsify" one of the sides of the equivalentTrip.
    * */
    private GtfsTripAdapter getTheOtherTrip(org.onebusaway.gtfs.model.Trip gtfsRawTrip) {
        return new GtfsTripAdapter(gtfsRawTrip) {
            @Override
            public Heading getHeading() {
                return Heading.reverse(super.getHeading());
            }

            @Override
            public String getDestinationSign() {
                String oldSign = super.getDestinationSign();
                String longName = gtfsRawTrip.getRoute().getLongName();

                String newSign = longName.replace(oldSign, "").trim();
                return newSign.replaceAll("^-|-$", "").trim();
            }
        };
    }
}
