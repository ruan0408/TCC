package com.smartsampa.busapi.impl;

import com.smartsampa.busapi.model.AbstractTrip;
import com.smartsampa.busapi.model.Heading;
import com.smartsampa.busapi.model.Provider;
import com.smartsampa.busapi.model.Shape;
import com.smartsampa.gtfsapi.GtfsAPI;
import org.apache.commons.math3.util.Precision;
import org.onebusaway.gtfs.model.ShapePoint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public class GtfsTrip extends AbstractTrip {

    private org.onebusaway.gtfs.model.Trip gtfsTrip;
    private GtfsAPI gtfsAPI = Provider.getGtfsAPI();

    public GtfsTrip(org.onebusaway.gtfs.model.Trip gtfsTrip) {
        this.gtfsTrip = gtfsTrip;
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
        double farePrice = gtfsAPI.getFarePrice(getNumberSign());
        return Precision.round(farePrice, 2);
    }

    @Override
    public Shape getShape() {
        List<ShapePoint> shapePoints = gtfsAPI.getShape(getShapeId());
        return new GtfsShape(shapePoints);
    }

    private String getShapeId() {
        return gtfsTrip.getShapeId().getId();
    }

    //TODO maybe it doesnt make sense to keep asking busapi for the gtfs
    @Override
    public int getDepartureIntervalInSecondsAtTime(String hhmm) {
        return gtfsAPI.getDepartureIntervalAtTime(getGtfsId(), hhmm);
    }

    @Override
    public int getDepartureIntervalInSecondsNow() {
        String hhmm = new SimpleDateFormat("HH:mm").format(new Date());
        return getDepartureIntervalInSecondsAtTime(hhmm);
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
    GtfsTrip cloneChangingHeadingAndDestinationSign() {
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
