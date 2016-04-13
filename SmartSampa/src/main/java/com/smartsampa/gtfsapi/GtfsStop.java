package com.smartsampa.gtfsapi;

import com.smartsampa.model.Stop;
import com.smartsampa.utils.Point;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public class GtfsStop extends Stop {

    private org.onebusaway.gtfs.model.Stop gtfsStop;

    public GtfsStop(org.onebusaway.gtfs.model.Stop gtfsStop) {
        this.gtfsStop = gtfsStop;
    }

    @Override
    public Point getLocation() {
        return new Point(gtfsStop.getLat(), gtfsStop.getLon());
    }

    @Override
    public String getName() {
        return gtfsStop.getName();
    }

    @Override
    public String getGtfsId() {
        return gtfsStop.getId().getId();
    }
}
