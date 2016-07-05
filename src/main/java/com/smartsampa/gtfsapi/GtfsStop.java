package com.smartsampa.gtfsapi;

import com.smartsampa.busapi.AbstractStop;
import com.smartsampa.utils.Point;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public class GtfsStop extends AbstractStop {

    private org.onebusaway.gtfs.model.Stop gtfsRawStop;

    public GtfsStop(org.onebusaway.gtfs.model.Stop gtfsRawStop) {
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
    public Point getLocation() {
        return new Point(gtfsRawStop.getLat(), gtfsRawStop.getLon());
    }

}
