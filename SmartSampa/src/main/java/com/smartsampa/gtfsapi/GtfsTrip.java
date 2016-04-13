package com.smartsampa.gtfsapi;

import com.smartsampa.busapi.BusAPIManager;
import com.smartsampa.model.Shape;
import com.smartsampa.model.Stop;
import com.smartsampa.model.Trip;
import org.onebusaway.gtfs.model.FareRule;
import org.onebusaway.gtfs.model.ShapePoint;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public class GtfsTrip extends Trip {

    private org.onebusaway.gtfs.model.Trip gtfsTrip;

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

    //TODO maybe remove these constants by creating a enum
    @Override
    public Integer getHeading() {
        return gtfsTrip.getDirectionId().equals("1") ? 1 : 2;
    }

    @Override
    public Boolean isCircular() {
        if (getDestinationSign().toLowerCase().contains("circular"))
            return true;

        return false;
    }

    @Override
    public String getWorkingDays() {
        return gtfsTrip.getServiceId().getId();
    }

    @Override
    public Double getFarePrice() {
        Predicate<FareRule> predicate;
        predicate = f -> f.getRoute().getId().getId().equals(getNumberSign());

        FareRule rule = BusAPIManager.gtfs.filterToElement("getAllFareRules", predicate);
        return (double)rule.getFare().getPrice();
    }

    @Override
    public Shape getShape() {
        Predicate<ShapePoint> predicate;
        predicate = point -> point.getShapeId().getId().equals(gtfsTrip.getShapeId().getId());

        List<ShapePoint> shapes = BusAPIManager.gtfs.filterToList("getAllShapePoints", predicate);

        return new GtfsShape(shapes);
    }

    //TODO Create an interface or abstract class (?) for Stop
    @Override
    public Set<Stop> getStops() {
        return super.getStops();
    }

    @Override
    public String getGtfsId() {
        return gtfsTrip.getId().getId();
    }

    protected GtfsTrip cloneChangingHeading() {
        GtfsTrip clone = new GtfsTrip (gtfsTrip) {
            @Override
            public Integer getHeading() {
                return super.getHeading() == 1 ? 2 : 1;
            }
            //TODO this needs to be fixed to work with names that contain '-'
            @Override
            public String getDestinationSign() {
                String[] names = gtfsTrip.getRoute().getLongName().split("-");
                names[0].trim();
                names[1].trim();
                return super.getDestinationSign().equalsIgnoreCase(names[0]) ? names[1] : names[0];
            }
        };
        return clone;
    }
}
