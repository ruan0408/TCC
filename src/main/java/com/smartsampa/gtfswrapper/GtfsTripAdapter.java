package com.smartsampa.gtfswrapper;

import com.smartsampa.busapi.Heading;
import com.smartsampa.busapi.Provider;
import com.smartsampa.busapi.Shape;
import com.smartsampa.busapi.Trip;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public class GtfsTripAdapter extends Trip {

    private org.onebusaway.gtfs.model.Trip gtfsRawTrip;
    private GtfsAPIFacade gtfsAPIFacade = Provider.getGtfsAPIFacade();

    public GtfsTripAdapter(org.onebusaway.gtfs.model.Trip gtfsRawTrip) {
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
        return gtfsAPIFacade.getFarePrice(getNumberSign());
    }

    @Override
    public Shape getShape() {
        return gtfsAPIFacade.getShape(getShapeId());
    }

    private String getShapeId() {
        return gtfsRawTrip.getShapeId().getId();
    }

    @Override
    public int getDepartureIntervalInSecondsAtTime(String HHmm) {
        return gtfsAPIFacade.getDepartureIntervalAtTime(getGtfsId(), HHmm);
    }

    @Override
    public int getDepartureIntervalInSecondsNow() {
        String HHmm = new SimpleDateFormat("HH:mm").format(new Date());
        return getDepartureIntervalInSecondsAtTime(HHmm);
    }

    @Override
    public String getGtfsId() {
        return gtfsRawTrip.getId().getId();
    }

}
