package com.smartsampa.busapi;

import com.smartsampa.gtfswrapper.GtfsAPIFacade;
import com.smartsampa.olhovivoapi.OlhovivoAPI;
import com.smartsampa.shapefileapi.ShapefileAPI;
import com.smartsampa.shapefileapi.ShapefileBusLane;
import org.opengis.feature.simple.SimpleFeature;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

/**
 * Created by ruan0408 on 15/07/2016.
 */
public class StaticAPI {

    private OlhovivoAPI olhovivoAPI;
    private GtfsAPIFacade gtfsAPIFacade;
    private ShapefileAPI shapefileAPI;
    private DataFiller dataFiller;

    public StaticAPI() {
        olhovivoAPI = Provider.getOlhovivoAPI();
        gtfsAPIFacade = Provider.getGtfsAPIFacade();
        shapefileAPI = Provider.getShapefileAPI();
        dataFiller = new DataFiller();
    }

    public Set<Trip> getTripsByTerm(String term) {
        Set<Trip> gtfsTrips = gtfsAPIFacade.getTripsByTerm(term);
        return gtfsTrips.stream().peek(dataFiller::fill).collect(toSet());
    }

    public Set<Stop> getStopsByTerm(String term) {
        Set<Stop> gtfsStops = gtfsAPIFacade.getStopsByTerm(term);
        return gtfsStops.stream().peek(dataFiller::fill).collect(toSet());
    }

    public Stop getStopById(int id) {
        Stop gtfsStop = gtfsAPIFacade.getStopById(id);
        dataFiller.fill(gtfsStop);
        return gtfsStop;
    }

    public Set<Trip> getTripsFromStop(Stop stop) {
        return gtfsAPIFacade.getTripsFromStop(stop).stream()
                .peek(dataFiller::fill)
                .collect(toSet());
    }

    public List<Stop> getStopsFromTrip(Trip trip) {
        return gtfsAPIFacade.getStopsFromTrip(trip).stream()
                .peek(dataFiller::fill)
                .collect(toList());
    }

    public Trip getTrip(String numberSign, Heading heading) {
        return getTripsByTerm(numberSign).stream()
                .filter(t -> t.getHeading() == heading)
                .findAny()
                .orElse(Trip.emptyTrip());
    }

    public List<Corridor> getAllCorridors() { return olhovivoAPI.getAllCorridors(); }

    public Corridor getCorridorByTerm(String term) {
        return getAllCorridors().stream()
                .filter(c -> containsIgnoreCase(c.getName(), term))
                .findAny()
                .orElse(Corridor.emptyCorridor());
    }

    public List<Stop> getStopsFromCorridor(Corridor corridor) {
        List<Stop> olhovivoStops = olhovivoAPI.getStopsByCorridor(corridor.getId());
        return olhovivoStops.stream().peek(dataFiller::fill).collect(toList());
    }

    public List<BusLane> getAllBusLanes() {
        return getAllShapefileBusLanes().collect(toList());
    }

    public List<BusLane> getBusLanesByTerm(String term) {
        return getAllShapefileBusLanes()
                .filter(lane -> lane.containsTerm(term))
                .collect(toList());
    }

    private Stream<ShapefileBusLane> getAllShapefileBusLanes() {
        Map<String, List<SimpleFeature>> groupedByName =
                shapefileAPI.groupBy(feature -> feature.getAttribute("nm_denomin").toString());

        return groupedByName.values().stream().map(ShapefileBusLane::new);
    }
}
