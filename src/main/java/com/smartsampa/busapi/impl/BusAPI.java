package com.smartsampa.busapi.impl;

import com.smartsampa.busapi.model.*;
import com.smartsampa.gtfsapi.GtfsAPI;
import com.smartsampa.gtfsapi.GtfsDownloader;
import com.smartsampa.gtfsapi.GtfsHandler;
import com.smartsampa.olhovivoapi.OlhoVivoAPI;
import com.smartsampa.shapefileapi.ShapefileAPI;
import org.opengis.feature.simple.SimpleFeature;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

/**
 * Created by ruan0408 on 30/04/2016.
 */
public final class BusAPI {

    public static GtfsAPI gtfs;
    public static OlhoVivoAPI olhovivo;
    public static ShapefileAPI shapefile;

    private static String sptransLogin;
    private static String sptransPassword;
    private static String olhovivoKey;

    private static final BusAPI ourInstance = new BusAPI();
    private BusAPI() {}

    public static void initialize() {
        GtfsDownloader gtfsDownloader = new GtfsDownloader(sptransLogin, sptransPassword);
        GtfsHandler gtfsHandler = new GtfsHandler(gtfsDownloader);
        gtfs = new GtfsAPI(gtfsHandler.getGtfsDao());

        //TODO put this in the same architecture as the others
        shapefile = new ShapefileAPI("faixa_onibus/sirgas_faixa_onibus.shp");

        olhovivo = new OlhoVivoAPI(olhovivoKey);
        olhovivo.authenticate();
    }

    public static void setSptransLogin(String login) {sptransLogin = login;}

    public static void setSptransPassword(String password) {sptransPassword = password;}

    public static void setOlhovivoKey(String key) {olhovivoKey = key;}

    public static Set<Trip> getTripsByTerm(String term) {
        Set<Trip> gtfsTrips = GtfsTrip.getGtfsTripsByTerm(term);
        Set<Trip> olhovivoTrips = olhovivo.getTripsByTerm(term);

        return Mergeable.mergeSets(gtfsTrips, olhovivoTrips);
    }

    public static Trip getTripById(String tripId) {
        Pattern pattern = Pattern.compile("(\\w+-\\d+)-([12])");
        Matcher matcher = pattern.matcher(tripId);
        matcher.find();
        String numberSign = matcher.group(1);
        int heading = Integer.parseInt(matcher.group(2));

        return getTrip(numberSign, Heading.getHeadingFromInt(heading));
    }

    //TODO returning null might be bad...
    public static Trip getTrip(String numberSign, Heading heading) {
        return getTripsByTerm(numberSign).stream()
                .filter(t -> t.getHeading() == heading)
                .findAny()
                .orElse(null);
    }

    public static Set<Stop> getStopsByTerm(String term) {
        Set<Stop> gtfsStops = GtfsStop.getStopsByTerm(term);
        Set<Stop> olhovivoStops = olhovivo.getStopsByTerm(term);
        return Mergeable.mergeSets(gtfsStops, olhovivoStops);
    }

    public static Stop getStopById(int id) {
        Stop gtfsStop = GtfsStop.getStopById(id);
        Set<Stop> stops = getStopsByTerm(gtfsStop.getName());
        return stops.parallelStream()
                .filter(gtfsStop::equals)
                .findAny()
                .orElse(null);
    }

    public static List<Corridor> getAllCorridors() {
        return olhovivo.getAllCorridors();
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
                shapefile.groupBy(feature -> feature.getAttribute("nm_denomin").toString());

        return groupedByName.values().stream().map(ShapefileBusLane::new);
    }
}
