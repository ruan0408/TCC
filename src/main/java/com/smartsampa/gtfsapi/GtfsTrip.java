package com.smartsampa.gtfsapi;

import com.smartsampa.busapi.AbstractTrip;
import com.smartsampa.busapi.Heading;
import com.smartsampa.busapi.Provider;
import com.smartsampa.busapi.Shape;
import org.apache.commons.math3.util.Precision;
import org.onebusaway.gtfs.model.ShapePoint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public class GtfsTrip extends AbstractTrip {

    private org.onebusaway.gtfs.model.Trip gtfsRawTrip;
    private GtfsAPI gtfsAPI = Provider.getGtfsAPI();

    public GtfsTrip(org.onebusaway.gtfs.model.Trip gtfsRawTrip) {
        this.gtfsRawTrip = gtfsRawTrip;
    }

    @Override
    public String getDestinationSign() {
        return gtfsRawTrip.getTripHeadsign();
    }

    @Override
    public String getNumberSign() {
        return gtfsRawTrip.getRoute().getShortName();
    }

    @Override
    public Heading getHeading() {
        return isHeadingToSecondaryTerminal() ? Heading.SECONDARY_TERMINAL : Heading.MAIN_TERMINAL;
    }

    private boolean isHeadingToSecondaryTerminal() {
        return gtfsRawTrip.getDirectionId().equals("0");
    }

    @Override
    public String getWorkingDays() {
        return gtfsRawTrip.getServiceId().getId();
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
        return gtfsRawTrip.getShapeId().getId();
    }

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
        return gtfsRawTrip.getId().getId();
    }

}
