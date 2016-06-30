package com.smartsampa.busapi.impl;

import com.smartsampa.busapi.model.AbstractStop;
import com.smartsampa.utils.Point;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public class GtfsStop extends AbstractStop {

    private org.onebusaway.gtfs.model.Stop gtfsStop;

    public GtfsStop(org.onebusaway.gtfs.model.Stop gtfsStop) {
        this.gtfsStop = gtfsStop;
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

}
