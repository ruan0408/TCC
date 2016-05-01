package com.smartsampa.busapi.model;

import com.smartsampa.busapi.impl.BusAPI;
import com.smartsampa.busapi.impl.BusAPIManager;
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

    private Integer id;
    private String name;
    private String address;
    private String reference;
    private Point location;
    private Set<Trip> trips;

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
            Trip newTrip = BusAPI.getTrip(oldTrip.getNumberSign(), oldTrip.getHeading());
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
    public void merge(Mergeable o) {
        Stop other = (Stop) o;
        if (other == null) return;
        if (getId() == null) setId(other.getId());
        if (getName() == null) setName(other.getName());
        if (getReference() == null) setReference(other.getReference());
        if (getAddress() == null) setAddress(other.getAddress());
        if (getLocation() == null) setLocation(other.getLocation());
        if (getTrips() == null) setTrips(other.getTrips());
    }

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
