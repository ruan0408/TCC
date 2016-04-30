package com.smartsampa.busapi2.model;

import com.smartsampa.busapi2.impl.BusAPIManager;
import com.smartsampa.busapi2.impl.GtfsStop;
import com.smartsampa.utils.Point;
import com.smartsampa.utils.Utils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.*;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public abstract class AbstractStop implements Stop {

    private Integer id;
    private String name;
    private String address;
    private String reference;
    private Point location;
    private Set<Trip> trips;

    public static Set<AbstractStop> searchStopsByTerm(String term) {
        Set<AbstractStop> gtfsStops = GtfsStop.getStopsByTerm(term);
        Set<AbstractStop> olhovivoStops = BusAPIManager.olhovivo.getStopsByTerm(term);
        return mergeSets(gtfsStops, olhovivoStops);
    }

    //TODO maybe move this code to superclass with getIds too.
    private static Set<AbstractStop> mergeSets(Set<AbstractStop> stops1, Set<AbstractStop> stops2) {
        Map<AbstractStop, AbstractStop> mirror2 = Utils.mirroredMap(stops2);
        Set<AbstractStop> result = new HashSet<>();

        for (AbstractStop stop : stops1) {
            AbstractStop equalStop = mirror2.get(stop);
            if (equalStop != null) {
                result.add(merge(stop, equalStop));
                mirror2.remove(equalStop);
            }
            else result.add(stop);
        }

        result.addAll(mirror2.keySet());
        return result;
    }

    private static AbstractStop merge(AbstractStop stop1, AbstractStop stop2) {
        if (stop1.getId() == null) stop1.setId(stop2.getId());
        if (stop1.getName() == null) stop1.setName(stop2.getName());
        if (stop1.getReference() == null) stop1.setReference(stop2.getReference());
        if (stop1.getAddress() == null) stop1.setAddress(stop2.getAddress());
        if (stop1.getLocation() == null) stop1.setLocation(stop2.getLocation());
        if (stop1.getTrips() == null) stop1.setTrips(stop2.getTrips());
        return stop1;
    }

    //TODO test this method
    public static Stop getStopById(int id) {
        AbstractStop gtfsStop = GtfsStop.getStopById(id);
        Set<AbstractStop> stops = searchStopsByTerm(gtfsStop.getName());
        return stops.stream()
                .filter(gtfsStop::equals)
                .findAny()
                .orElse(null);
    }

    //TODO test that the trips are complete
    @Override
    public Map<Trip, List<PredictedBus>> getAllPredictions() {
        Map<AbstractTrip, List<PredictedBus>> predictions =
                BusAPIManager.olhovivo.getPredictionsAtStop(getId());

        return replaceTripByCompleteTrip(predictions);
    }

    private Map<Trip, List<PredictedBus>> replaceTripByCompleteTrip(Map<AbstractTrip, List<PredictedBus>> predictions) {
        Map<Trip, List<PredictedBus>> result = new HashMap<>(predictions.size());
        predictions.keySet().stream().forEach(oldTrip -> {
            Trip newTrip = AbstractTrip.getTrip(oldTrip.getNumberSign(), oldTrip.getHeading());
            result.put(newTrip, predictions.get(oldTrip));
        });
        return result;
    }

    @Override
    public List<PredictedBus> getPredictedBusesOfTrip(Trip trip) {
        return BusAPIManager.olhovivo.getPredictionsOfTripAtStop(trip.getOlhovivoId(), getId());
    }

    @Override
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {this.name = name;}

    @Override
    public String getReference() {return reference;}
    public void setReference(String reference) {this.reference = reference;}

    @Override
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    @Override
    public Point getLocation() {return location;}
    public void setLocation(Point location) {this.location = location;}

    @Override
    public Set<Trip> getTrips() {return trips;}
    public void setTrips(Set<Trip> trips) {this.trips = trips;}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractStop)) return false;

        AbstractStop that = (AbstractStop) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getId();
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", getId())
                .append("name", getName())
                .append("address", getAddress())
                .append("reference", getReference())
                .append("location", getLocation())
                .toString();
    }
}
