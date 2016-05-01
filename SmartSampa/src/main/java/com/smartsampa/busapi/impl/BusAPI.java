package com.smartsampa.busapi.impl;

import com.smartsampa.busapi.model.*;
import org.opengis.feature.simple.SimpleFeature;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

/**
 * Created by ruan0408 on 30/04/2016.
 */
public class BusAPI {

    public static Set<Trip> getTripsByTerm(String term) {
        Set<Trip> gtfsTrips = GtfsTrip.getGtfsTripsByTerm(term);
        Set<Trip> olhovivoTrips = BusAPIManager.olhovivo.getTripsByTerm(term);
        return Mergeable.mergeSets(gtfsTrips, olhovivoTrips);
    }

    public static Trip getTrip(String numberSign, Heading heading) {
        return getTripsByTerm(numberSign).stream()
                .filter(t -> t.getHeading() == heading)
                .findAny()
                .orElse(null);
    }

    public static Set<Stop> getStopsByTerm(String term) {
        Set<Stop> gtfsStops = GtfsStop.getStopsByTerm(term);
        Set<Stop> olhovivoStops = BusAPIManager.olhovivo.getStopsByTerm(term);
        return Mergeable.mergeSets(gtfsStops, olhovivoStops);
    }

    //TODO test this method
    public static Stop getStopById(int id) {
        Stop gtfsStop = GtfsStop.getStopById(id);
        Set<Stop> stops = getStopsByTerm(gtfsStop.getName());
        return stops.stream()
                .filter(gtfsStop::equals)
                .findAny()
                .orElse(null);
    }

    public static List<Corridor> getAllCorridors() {
        return BusAPIManager.olhovivo.getAllCorridors();
    }

    public static Corridor getCorridorByTerm(String term) {
        return getAllCorridors().stream()
                .filter(c -> containsIgnoreCase(c.getName(), term))
                .findAny()
                .orElse(null);
    }

    public static List<BusLane> getAllBusLanes() {
        return getAllShapefileBusLanes().collect(Collectors.toList());
    }

    public static List<BusLane> getBusLanesByTerm(String term) {
        return getAllShapefileBusLanes()
                .filter(lane -> lane.containsTerm(term))
                .collect(Collectors.toList());
    }

    private static Stream<ShapefileBusLane> getAllShapefileBusLanes() {
        Map<String, List<SimpleFeature>> groupedByName =
                BusAPIManager.shapefile.groupBy(feature -> feature.getAttribute("nm_denomin").toString());

        return groupedByName.values().stream().map(ShapefileBusLane::new);
    }
}
