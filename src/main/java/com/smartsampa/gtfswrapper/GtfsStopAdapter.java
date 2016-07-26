package com.smartsampa.gtfswrapper;

import com.smartsampa.busapi.Stop;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public class GtfsStopAdapter extends Stop {

    private org.onebusaway.gtfs.model.Stop gtfsRawStop;

    public GtfsStopAdapter(org.onebusaway.gtfs.model.Stop gtfsRawStop) {
        this.gtfsRawStop = gtfsRawStop;
    }

    @Override
    public Integer getId() {
        return Integer.parseInt(gtfsRawStop.getId().getId());
    }

    @Override
    public String getName() {
        return gtfsRawStop.getName();
    }

    @Override
    public String getReference() {return gtfsRawStop.getDesc();}

    @Override
    public Double getLatitude() { return gtfsRawStop.getLat(); }

    @Override
    public Double getLongitude() { return gtfsRawStop.getLon(); }

    //    @Override
//    public Point getLocation() {
//        return new Point(gtfsRawStop.getLat(), gtfsRawStop.getLon());
//    }

}
