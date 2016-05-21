package com.smartsampa.busapi.impl;

import com.smartsampa.busapi.model.*;
import org.apache.commons.math3.util.Precision;
import org.onebusaway.gtfs.model.ShapePoint;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public class GtfsTrip extends AbstractTrip {

    private org.onebusaway.gtfs.model.Trip gtfsTrip;

    public GtfsTrip(org.onebusaway.gtfs.model.Trip gtfsTrip) {
        this.gtfsTrip = gtfsTrip;
    }

    static Set<Trip> getGtfsTripsByTerm(String term) {
        return BusAPIManager.gtfs.getTripsWithRouteContaining(term).stream()
                .map(GtfsTrip::new)
                .flatMap(trip -> Stream.of(trip, trip.cloneChangingHeadingAndDestinationSign()))
                .filter(trip -> trip.containsTerm(term))
                .collect(Collectors.toSet());
    }

    @Override
    public String getDestinationSign() {
        return gtfsTrip.getTripHeadsign();
    }

    @Override
    public String getNumberSign() {
        return gtfsTrip.getRoute().getShortName();
    }

    @Override
    public Heading getHeading() {
        return isHeadingToSecondaryTerminal() ? Heading.SECONDARY_TERMINAL : Heading.MAIN_TERMINAL;
    }

    private boolean isHeadingToSecondaryTerminal() {
        return gtfsTrip.getDirectionId().equals("0");
    }

    @Override
    public String getWorkingDays() {
        return gtfsTrip.getServiceId().getId();
    }

    @Override
    public Double getFarePrice() {
        double farePrice = BusAPIManager.gtfs.getFarePrice(getNumberSign());
        return Precision.round(farePrice, 2);
    }

    @Override
    public Shape getShape() {
        List<ShapePoint> shapePoints = BusAPIManager.gtfs.getShape(getShapeId());
        return new GtfsShape(shapePoints);
    }

    private String getShapeId() {
        return gtfsTrip.getShapeId().getId();
    }

    @Override
    public List<Stop> getStops() {
        return BusAPIManager.gtfs.getAllStopsOrderedFromTripId(getGtfsId())
                .stream()
                .map(GtfsStop::new)
                .collect(toList());
    }

    @Override
    public int getDepartureIntervalInSecondsAtTime(String hhmm) {
        return BusAPIManager.gtfs.getDepartureIntervalAtTime(getGtfsId(), hhmm);
    }

    @Override
    public String getGtfsId() {
        return gtfsTrip.getId().getId();
    }


    /*
    * This method creates a clone of this GtfsTrip, but changes the heading
    * and the destinationSign. It's necessary because the gtfs files have only one entry
    * for circular trips, while the Olhovivo API has two entries for circular trips.
    * Therefore we "falsify" one of the sides of the trip.
    * */
    private GtfsTrip cloneChangingHeadingAndDestinationSign() {
        return new GtfsTrip (gtfsTrip) {
            @Override
            public Heading getHeading() {
                return Heading.reverse(super.getHeading());
            }

            @Override
            public String getDestinationSign() {
                String oldSign = super.getDestinationSign();
                String longName = gtfsTrip.getRoute().getLongName();

                String newSign = longName.replace(oldSign, "").trim();
                return newSign.replaceAll("^-|-$", "").trim();
            }
        };
    }
}
