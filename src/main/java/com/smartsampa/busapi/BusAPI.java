package com.smartsampa.busapi;

import com.smartsampa.gtfsapi.GtfsAPI;
import com.smartsampa.gtfsapi.GtfsAPIWrapper;
import com.smartsampa.gtfsapi.GtfsDownloader;
import com.smartsampa.gtfsapi.GtfsHandler;
import com.smartsampa.olhovivoapi.OlhoVivoAPI;
import com.smartsampa.shapefileapi.ShapefileAPI;
import com.smartsampa.shapefileapi.ShapefileBusLane;
import org.opengis.feature.simple.SimpleFeature;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

/**
 * Created by ruan0408 on 30/04/2016.
 */
public final class BusAPI {

    private static final Pattern tripIdPattern = Pattern.compile("(\\w+-\\d+)-([12])");
    private static GtfsAPIWrapper gtfsAPIWrapper;
    private static OlhoVivoAPI olhovivo;
    private static ShapefileAPI shapefile;

    private static String sptransLogin;
    private static String sptransPassword;
    private static String olhovivoKey;

    private BusAPI() {}

    public static void initialize() {
        GtfsDownloader gtfsDownloader = new GtfsDownloader(sptransLogin, sptransPassword);
        GtfsHandler gtfsHandler = new GtfsHandler(gtfsDownloader);
        GtfsAPI gtfs = new GtfsAPI(gtfsHandler);
        gtfsAPIWrapper = new GtfsAPIWrapper(gtfs);

        //TODO put this in the same architecture as the others
        shapefile = new ShapefileAPI("faixa_onibus/sirgas_faixa_onibus.shp");

        olhovivo = new OlhoVivoAPI(olhovivoKey);
        olhovivo.authenticate();

        Provider.setGtfsAPI(gtfs);
        Provider.setOlhovivoAPI(olhovivo);
    }

    public static void setSptransLogin(String login) {sptransLogin = login;}

    public static void setSptransPassword(String password) {sptransPassword = password;}

    public static void setOlhovivoKey(String key) {olhovivoKey = key;}

    public static Set<Trip> getTripsByTerm(String term) {
        Set<Trip> gtfsTrips = gtfsAPIWrapper.getTripsByTerm(term);
        Set<Trip> olhovivoTrips = olhovivo.getTripsByTerm(term);

        return Mergeable.mergeSets(gtfsTrips, olhovivoTrips);
    }

    public static Set<Stop> getStopsByTerm(String term) {
        Set<Stop> gtfsStops = gtfsAPIWrapper.getStopsByTerm(term);
        Set<Stop> olhovivoStops = olhovivo.getStopsByTerm(term);
        return Mergeable.mergeSets(gtfsStops, olhovivoStops);
    }

    public static Stop getStopById(int id) {
        Stop gtfsStop = gtfsAPIWrapper.getStopById(id);
        Set<Stop> stops = getStopsByTerm(gtfsStop.getName());
        return stops.stream()
                .filter(gtfsStop::equals)
                .findAny()
                .orElse(Stop.emptyStop());
    }

    public static Trip getTripById(String id) {
        String numberSign = getNumberSignFromTripId(id);
        Heading heading = getHeadingFromTripId(id);
        return getTrip(numberSign, heading);
    }

    private static String getNumberSignFromTripId(String tripId) {
        Matcher matcher = tripIdPattern.matcher(tripId);
        return matcher.find() ? matcher.group(1) : null;
    }

    private static Heading getHeadingFromTripId(String tripId) {
        Matcher matcher = tripIdPattern.matcher(tripId);
        if (matcher.find()) {
            int heading = Integer.parseInt(matcher.group(2));
            return Heading.getHeadingFromInt(heading);
        }
        return null;
    }

    static Set<Trip> getTripsFromStop(Stop stop) {
        return gtfsAPIWrapper.getTripsFromStop(stop).stream()
                .map(trip -> getTrip(trip.getNumberSign(), trip.getHeading()))
                .collect(toSet());
    }

    static Trip getTrip(String numberSign, Heading heading) {
        return getTripsByTerm(numberSign).stream()
                .filter(t -> t.getHeading() == heading)
                .findAny()
                .orElse(Trip.emptyTrip());
    }

    //TODO make this return complete stops
    static List<Stop> getStopsFromTrip(Trip trip) {
        return gtfsAPIWrapper.getStopsFromTrip(trip);
    }


    public static List<Corridor> getAllCorridors() {
        return olhovivo.getAllCorridors();
    }


    public static Corridor getCorridorByTerm(String term) {
        return getAllCorridors().stream()
                .filter(c -> containsIgnoreCase(c.getName(), term))
                .findAny()
                .orElse(Corridor.emptyCorridor());
    }

    static List<Stop> getStopsFromCorridor(Corridor corridor) {
        List<Stop> olhovivoStops = olhovivo.getStopsByCorridor(corridor.getId());
        return olhovivoStops.stream()
                .map(olhovivoStop -> {
                    Stop gtfsStop = gtfsAPIWrapper.getStopsByTerm(olhovivoStop.getName()).stream()
                            .filter(olhovivoStop::equals)
                            .findAny()
                            .orElse(Stop.emptyStop());
                    olhovivoStop.merge(gtfsStop);
                    return olhovivoStop;
                })
                .collect(toList());
    }

    public static List<BusLane> getAllBusLanes() {
        return getAllShapefileBusLanes().collect(toList());
    }

    public static List<BusLane> getBusLanesByTerm(String term) {
        return getAllShapefileBusLanes()
                .filter(lane -> lane.containsTerm(term))
                .collect(toList());
    }

    private static Stream<ShapefileBusLane> getAllShapefileBusLanes() {
        Map<String, List<SimpleFeature>> groupedByName =
                shapefile.groupBy(feature -> feature.getAttribute("nm_denomin").toString());

        return groupedByName.values().stream().map(ShapefileBusLane::new);
    }
}
