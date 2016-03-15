package com.intellij.busapi;

import com.intellij.gtfsapi.GtfsAPI;
import com.intellij.olhovivoapi.BusCorridor;
import com.intellij.olhovivoapi.BusLine;
import com.intellij.olhovivoapi.BusNow;
import com.intellij.olhovivoapi.BusStop;
import org.onebusaway.gtfs.model.ShapePoint;
import org.onebusaway.gtfs.model.StopTime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 12/03/2016.
 */
public class DataToAPIConverter {

    public static List<Stop> stopTimesToStops(List<StopTime> stopTimes) {
        return stopTimes.parallelStream()
                        .map(stopTime -> gtfsStopToStop(stopTime.getStop()))
                        .collect(Collectors.toList());
    }

    public static
    Stop gtfsStopToStop(org.onebusaway.gtfs.model.Stop gtfsStop) {
        Stop newStop = new Stop();
        newStop.setId(Integer.parseInt(gtfsStop.getId().getId()));
        newStop.setName(gtfsStop.getName());
        newStop.setAddress("");
        newStop.setLocation(new Point(gtfsStop.getLat(), gtfsStop.getLon()));
        return newStop;
    }

    public static List<Stop> busStopArrayToStops(BusStop[] busStops) {
        if (busStops == null) return new ArrayList<>();

        List<Stop> stops = new ArrayList<>(busStops.length);

        for (int i = 0; i < busStops.length; i++)
            stops.add(busStopToStop(busStops[i]));

        return stops;
    }

    public static Stop busStopToStop(BusStop busStop) {
        Stop newStop = new Stop();
        newStop.setId(busStop.getCode());
        newStop.setName(busStop.getName());
        newStop.setAddress(busStop.getAddress());
        newStop.setLocation(new Point(busStop.getLatitude(), busStop.getLongitude()));
        return newStop;
    }

    public static Shape shapePointsToShape(List<ShapePoint> shapes) {
        shapes.sort(GtfsAPI.compByShapePointSequence);

        Point[] points = new Point[shapes.size()];
        double[] distances = new double[shapes.size()];

        for (int i = 0; i < shapes.size(); i++) {
            ShapePoint p = shapes.get(i);
            points[i] = new Point(p.getLat(), p.getLon());
            distances[i] = p.getDistTraveled();
        }
        return new Shape(points, distances);
    }

    protected static List<Trip> busLinesArrayToTrips(BusLine[] busLines) {
        if (busLines == null) return new ArrayList<>();
        List<Trip> trips = new ArrayList<>(busLines.length);

        for (int i = 0; i < busLines.length; i++)
            trips.add(busLineToTrip(busLines[i]));

        return trips;
    }

    protected static List<Bus>
    olhovivoBusArrayToBuses(com.intellij.olhovivoapi.Bus[] busArray) {
        return convertArrayToListOfClass(busArray, Bus.class);
    }

    protected static List<PredictedBus>
    busNowArrayToPredictedBuses(BusNow[] buses) {
        if (buses == null) return new ArrayList<>();
        List<PredictedBus> list = convertArrayToListOfClass(buses, PredictedBus.class);

        for (int i = 0; i < buses.length; i++)
            list.get(i).setPredictedArrival(buses[i].getPredictedArrivalTime());

        return list;
    }

    protected static Trip busLineToTrip(BusLine line) {
        String fullNumberSign = line.getNumberSign()+"-"+line.getType();

        //due to weird behaviour of olhovivo
        if (line.getNumberSign().matches("\\w{4}-\\w{2}"))
            fullNumberSign = line.getNumberSign();

        String heading = line.getHeading() == 1 ? "mtst" : "stmt";

        return Trip.getTrip(fullNumberSign, heading);
    }

    protected static
    List<Trip> gtfsTripToTrips(List<org.onebusaway.gtfs.model.Trip> list) {

        List<Trip> trips = list.parallelStream()
                .map(t -> gtfsTripToTrip(t))
                .collect(Collectors.toList());
        return trips;
    }

    protected static
    Trip gtfsTripToTrip(org.onebusaway.gtfs.model.Trip trip) {
        String fullNumberSign = trip.getRoute().getShortName();
        String heading = trip.getDirectionId().equals("1") ? "mtst" : "stmt";

        return Trip.getTrip(fullNumberSign, heading);
    }

    protected static List<Corridor> busCorridorArrayToCorridors(BusCorridor[] corridors) {
        if (corridors == null) return new ArrayList<>();
        List<Corridor> list = new ArrayList<>(corridors.length);

        for (int i = 0; i < corridors.length; i++)
            list.add(busCorridorToCorridor(corridors[i]));

        return list;
    }

    private static Corridor busCorridorToCorridor(BusCorridor corridor) {
        return new Corridor(corridor.getCode(), corridor.getName());
    }

    private static <T extends Bus, K extends com.intellij.olhovivoapi.Bus>
    List<T> convertArrayToListOfClass(K[] buses, Class<T> tClass) {
        if (buses == null) return new ArrayList<>();
        List<T> list = new ArrayList<>(buses.length);

        for (int i = 0; i < buses.length; i++)
            list.add(buildFromAPIBus(buses[i], tClass));

        return list;
    }

    private static <T extends Bus>
    T buildFromAPIBus(com.intellij.olhovivoapi.Bus bus, Class<T> tClass) {
        T newBus = null;
        try {
            newBus = tClass.newInstance();
            newBus.setPrefix(bus.getPrefixNumber());
            newBus.setWheelChairCapable(bus.isWheelChairCapable());
            newBus.setLocation(new Point(bus.getLatitude(), bus.getLongitude()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newBus;
    }
}
