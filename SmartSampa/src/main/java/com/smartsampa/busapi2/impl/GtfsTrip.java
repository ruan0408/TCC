package com.smartsampa.busapi2.impl;

import com.smartsampa.busapi2.model.*;
import org.apache.commons.math3.util.Precision;
import org.onebusaway.gtfs.model.ShapePoint;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public class GtfsTrip extends AbstractTrip {

    private org.onebusaway.gtfs.model.Trip gtfsTrip;

    public GtfsTrip(org.onebusaway.gtfs.model.Trip gtfsTrip) {this.gtfsTrip = gtfsTrip;}

    public static Set<AbstractTrip> getGtfsTripsByTerm(String term) {
        Set<GtfsTrip> trips = BusAPIManager.gtfs.getTripsByTerm(term).stream()
                .map(GtfsTrip::new)
                .collect(toSet());

        Set<AbstractTrip> clonedTrips = trips.stream()
                .filter(GtfsTrip::isCircular)
                .map(GtfsTrip::cloneChangingHeading)
                .collect(toSet());

        clonedTrips.addAll(trips);
        return clonedTrips;
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
    public Boolean isCircular() {
        return BusAPIManager.gtfs.isTripCircular(gtfsTrip.getRoute().getShortName());
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

    protected GtfsTrip cloneChangingHeading() {
        GtfsTrip clone = new GtfsTrip (gtfsTrip) {
            @Override
            public Heading getHeading() {
                return Heading.reverse(super.getHeading());
            }

            //TODO this needs to be fixed to work with names that contain '-'
            @Override
            public String getDestinationSign() {
                String[] names = gtfsTrip.getRoute().getLongName().split("-");
                String mtSign = names[0].trim();
                String stSign = names[1].trim();
                return getHeading() == Heading.SECONDARY_TERMINAL ? stSign : mtSign;
            }
        };
        return clone;
    }
}
