package com.smartsampa.busapi;

import com.smartsampa.olhovivoapi.OlhoVivoAPI;
import com.smartsampa.utils.Point;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public abstract class AbstractStop implements Stop {

    private static OlhoVivoAPI olhoVivoAPI = Provider.getOlhovivoAPI();

    private Integer id;
    private String name;
    private String address;
    private String reference;
    private Point location;

    //TODO test that the trips are complete
    @Override
    public Map<Trip, List<PredictedBus>> getPredictionsPerTrip() {
        Map<Trip, List<PredictedBus>> predictions =
                olhoVivoAPI.getPredictionsAtStop(getId());

        return replaceTripsByCompleteTrips(predictions);
    }

    private Map<Trip, List<PredictedBus>> replaceTripsByCompleteTrips(Map<Trip, List<PredictedBus>> predictions) {
        Map<Trip, List<PredictedBus>> result = new HashMap<>(predictions.size());
        predictions.keySet().stream().forEach(oldTrip -> {
            Trip newTrip = BusAPI.getTrip(oldTrip.getNumberSign(), oldTrip.getHeading());
            result.put(newTrip, predictions.get(oldTrip));
        });
        return result;
    }

    @Override
    public List<PredictedBus> getPredictedBusesOfTrip(Trip trip) {
        return olhoVivoAPI.getPredictionsOfTripAtStop(trip.getOlhovivoId(), getId());
    }

    @Override
    public Set<Trip> getTrips() {
        return BusAPI.getTripsFromStop(this);
    }

    @Override
    public void merge(Mergeable mergeable) {
        if (mergeable == null) return;

        Stop other = (Stop) mergeable;
        if (getId() == null) id = other.getId();
        if (getName() == null) name = other.getName();
        if (getReference() == null) reference = other.getReference();
        if (getAddress() == null) address = other.getAddress();
        if (getLocation() == null) location = other.getLocation();
    }

    @Override
    public Integer getId() { return id; }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getReference() {return reference;}

    @Override
    public String getAddress() {return address;}

    @Override
    public Point getLocation() {return location;}

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
