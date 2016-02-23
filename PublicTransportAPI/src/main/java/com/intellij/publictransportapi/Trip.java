package com.intellij.publictransportapi;

import com.intellij.olhovivoapi.*;
import org.apache.commons.lang.text.StrBuilder;
import org.onebusaway.gtfs.model.Frequency;
import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.StopTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class Trip {

    private int internalId;
    private String shapeId = null;
    private Route route;
    private String destinationSign;

    public void setInternalId(int internalId) {this.internalId = internalId;}

    public void setDestinationSign(String destinationSign) {this.destinationSign = destinationSign;}

    public void setRoute(Route route) { this.route = route;}

    public void setShapeId(String shapeId) {
        this.shapeId = shapeId;
    }

    public int getInternalId() {return internalId;}

    public Route getRoute() {return route;}

    public String getGtfsId() {
        if (route.getTripMTST().equals(this) && !route.isCircular())
            return route.fullNumberSign()+"-1";

        return route.fullNumberSign()+"-0";
    }

    public String getShapeId() {
        if (shapeId == null) {
            Predicate<org.onebusaway.gtfs.model.Trip> predicate;
            predicate = trip -> trip.getId().getId().equals(getGtfsId());

            org.onebusaway.gtfs.model.Trip trip =
                    API.filterGtfsToElement("getAllTrips", predicate);

            shapeId = trip.getShapeId().getId();
        }
        return shapeId;
    }

    public String getDestinationSign() { return destinationSign;}

    public String getWorkingDays() {
        Predicate<org.onebusaway.gtfs.model.Trip> predicate;
        predicate = t -> t.getId().getId().equals(getGtfsId());

        org.onebusaway.gtfs.model.Trip trip =
                API.filterGtfsToElement("getAllTrips", predicate);

        return trip.getServiceId().getId();
    }

    public String getDetails() {
        return API.getTripDetails(internalId);
    }

    public Shape getShape() {
        Predicate<ShapePoint> predicate;
        predicate = point -> point.getShapeId().getId().equals(getShapeId());

        List<ShapePoint> shapes = API.filterGtfsToList("getAllShapePoints", predicate);

        return Shape.convert(shapes);
    }

    public List<Stop> getAllStops() {
        Predicate<StopTime> predicate;
        predicate = s -> s.getTrip().getId().getId().equals(getGtfsId());

        List<StopTime> filtered = API.filterGtfsToList("getAllStopTimes", predicate);

        filtered.sort(Utils.compByStopSequence);

        List<Stop> stopsFromGtfs = Stop.convert(Utils.convert(filtered));
        List<Stop> stopsFromOlhoVivo = Stop.convert(API.getStopsByTrip(internalId));

        Map<Stop, Stop> map = new Hashtable<>(stopsFromOlhoVivo.size());

        for (Stop stop : stopsFromOlhoVivo) map.put(stop, stop);

        for (Stop stop : stopsFromGtfs) {
            Stop fromOlhoVivo = map.get(stop);
            if (fromOlhoVivo != null)
                stop.setAddress(fromOlhoVivo.getAddress());
        }

        return stopsFromGtfs;
    }

    public List<com.intellij.publictransportapi.Bus> getAllBuses() {
        BusLinePositions busesWithTrip = API.getBusesByTrip(internalId);
        return com.intellij.publictransportapi.Bus.convert(busesWithTrip.getVehicles(), com.intellij.publictransportapi.Bus.class);
    }

    public List<PredictedBus> getPredictedBuses(Stop stop) {
        ForecastWithStopAndLine forecast =
                API.getForecastByStopAndTrip(internalId, stop.getId());

        if (forecast.getBuses() == null) return new ArrayList<>();

        return PredictedBus.convert(forecast.getBuses());
    }

    public Map<Stop, List<PredictedBus>> getAllPredictions() {
        ForecastWithLine forecast = API.getForecastByTrip(internalId);

        BusStopNow[] busStopNowArray = forecast.getBusStops();
        Map<Stop, List<PredictedBus>> map = new HashMap<>(busStopNowArray.length);

        for (int i = 0; i < busStopNowArray.length; i++) {
            BusStopNow stopNow = busStopNowArray[i];
            map.put(Stop.buildFromBusStop(stopNow.getBusStop()),
                    PredictedBus.convert(stopNow.getVehicles()));
        }
        return map;
    }

    public int getDepartureInterval(String hhmm) {
        try {
            Date date = new SimpleDateFormat("HH:mm").parse(hhmm);
            Date date0 = new SimpleDateFormat("HH:mm").parse("00:00");
            long time = (date.getTime() - date0.getTime())/1000;

            return getDepartureInterval(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getDepartureIntervalNow() {

        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return getDepartureInterval(time);
    }

    protected static
    Trip buildFrom(org.onebusaway.gtfs.model.Trip trip) {
        Route parentRoute = Route.buildFrom(trip.getRoute().getShortName());

        if (trip.getDirectionId().equals("1"))
            return parentRoute.getTripMTST();

        return parentRoute.getTripSTMT();
    }

    protected static Trip buildFrom(BusLine line) {
        Route parentRoute = Route.buildFrom(line.getNumberSign());

        if (line.getHeading() == 1)
            return parentRoute.getTripMTST();

        return parentRoute.getTripSTMT();
    }

    private int getDepartureInterval(long time) {
        Predicate<Frequency> predicate;
        predicate = f -> f.getTrip().getId().getId().equals(getGtfsId()) &&
                (f.getStartTime() <= time) && (time < f.getEndTime());

        Frequency f = API.filterGtfsToElement("getAllFrequencies", predicate);
        return f.getHeadwaySecs();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trip)) return false;

        Trip that = (Trip) obj;
        if (this.internalId != that.internalId) return false;

        return true;
    }

    @Override
    public String toString() {
        StrBuilder builder = new StrBuilder();
        builder.append(route.basicToString());
        builder.appendln("internalId: "+ internalId);
        builder.appendln("destinationSign: "+destinationSign);

        return builder.toString();
    }
}
