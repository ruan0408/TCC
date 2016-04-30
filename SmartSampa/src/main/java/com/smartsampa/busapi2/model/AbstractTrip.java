package com.smartsampa.busapi2.model;

import com.smartsampa.busapi2.impl.BusAPIManager;
import com.smartsampa.busapi2.impl.GtfsTrip;
import com.smartsampa.utils.Utils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public abstract class AbstractTrip implements Trip {

    private String destinationSign;
    private String numberSign;
    private String workingDays;
    private Heading heading;
    private Shape shape;
    private List<Stop> stops;
    private Double farePrice;
    private Boolean isCircular;

    private Integer olhovivoId;
    private String gtfsId;

    public static Set<Trip> getTripsByTerm(String term) {
        Set<AbstractTrip> gtfsTrips = GtfsTrip.getGtfsTripsByTerm(term);
        Set<AbstractTrip> olhovivoTrips = BusAPIManager.olhovivo.getTripsByTerm(term);
        return mergeSets(gtfsTrips, olhovivoTrips);
    }

    //TODO refactor this method
    private static Set<Trip> mergeSets(Set<AbstractTrip> trips1, Set<AbstractTrip> trips2) {
        Map<AbstractTrip, AbstractTrip> mirror2 = Utils.mirroredMap(trips2);
        Set<Trip> mergedSets = new HashSet<>();

        for (AbstractTrip trip : trips1) {
            AbstractTrip equalTrip = mirror2.get(trip);
            if (equalTrip != null) {
                mergedSets.add(merge(trip, equalTrip));
                mirror2.remove(equalTrip);
            }
            else mergedSets.add(trip);
        }
        mergedSets.addAll(mirror2.keySet().stream().collect(Collectors.toSet()));

        return mergedSets;
    }

    private static AbstractTrip merge(AbstractTrip trip1, AbstractTrip trip2) {
        if (trip1.getDestinationSign() == null) trip1.setDestinationSign(trip2.getDestinationSign());
        if (trip1.getFarePrice() == null) trip1.setFarePrice(trip2.getFarePrice());
        if (trip1.getGtfsId() == null) trip1.setGtfsId(trip2.getGtfsId());
        if (trip1.getHeading() == null) trip1.setHeading(trip2.getHeading());
        if (trip1.getNumberSign() == null) trip1.setNumberSign(trip2.getNumberSign());
        if (trip1.getOlhovivoId() == null) trip1.setOlhovivoId(trip2.getOlhovivoId());
        if (trip1.getShape() == null) trip1.setShape(trip2.getShape());
        if (trip1.getStops() == null) trip1.setStops(trip2.getStops());
        if (trip1.getWorkingDays() == null) trip1.setWorkingDays(trip2.getWorkingDays());
        if (trip1.isCircular() == null) trip1.setCircular(trip2.isCircular());
        return trip1;
    }

    public static Trip getTrip(String numberSign, Heading heading) {
        return getTripsByTerm(numberSign).stream()
                .filter(t -> t.getHeading() == heading)
                .findAny()
                .orElse(null);
    }

    @Override
    public Map<Stop, List<PredictedBus>> getAllPredictions() {
        Map<AbstractStop, List<PredictedBus>> predictions =
                BusAPIManager.olhovivo.getPredictionsOfTrip(getOlhovivoId());

        return replaceStopsByCompleteStop(predictions);
    }

    private Map<Stop, List<PredictedBus>> replaceStopsByCompleteStop(Map<AbstractStop, List<PredictedBus>> predictions) {
        Map<Stop, List<PredictedBus>> result = new HashMap<>(predictions.size());

        predictions.keySet().stream().forEach(oldStop -> {
            Stop newStop = AbstractStop.getStopById(oldStop.getId());
            result.put(newStop, predictions.get(oldStop));
        });
        return result;
    }

    @Override
    public List<PredictedBus> getPredictionsAtStop(Stop stop) {
        return BusAPIManager.olhovivo.getPredictionsOfTripAtStop(stop.getId(), getOlhovivoId());
    }

    @Override
    public Set<Bus> getAllRunningBuses() {return BusAPIManager.olhovivo.getAllRunningBusesOfTrip(getOlhovivoId());}

    @Override
    public List<Stop> getStops() { return stops; }
    public void setStops(List<Stop> stops) {this.stops = stops;}

    @Override
    public Shape getShape() {return shape;}
    public void setShape(Shape shape) {this.shape = shape;}

    @Override
    public int getDepartureIntervalInSecondsAtTime(String hhmm) { return -1; }

    @Override
    public String getNumberSign() {return numberSign;}
    public void setNumberSign(String numberSign) {this.numberSign = numberSign;}

    @Override
    public Heading getHeading() {return heading;}
    public void setHeading(Heading heading) {this.heading = heading;}

    @Override
    public String getDestinationSign() {return destinationSign;}
    public void setDestinationSign(String destinationSign) {this.destinationSign = destinationSign;}

    @Override
    public String getWorkingDays() {return workingDays;}
    public void setWorkingDays(String workingDays) {this.workingDays = workingDays;}

    @Override
    public Double getFarePrice() {return farePrice;}
    public void setFarePrice(Double farePrice) {this.farePrice = farePrice;}

    @Override
    public Boolean isCircular() {return isCircular;}
    public void setCircular(boolean circular) {isCircular = circular;}

    @Override
    public Integer getOlhovivoId() {return olhovivoId;}
    public void setOlhovivoId(Integer olhovivoId) {this.olhovivoId = olhovivoId;}

    @Override
    public String getGtfsId() {return gtfsId;}
    public void setGtfsId(String gtfsId) {this.gtfsId = gtfsId;}

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AbstractTrip)) return false;

        AbstractTrip that = (AbstractTrip) o;
        return getNumberSign().equals(that.getNumberSign()) &&
                getHeading() == that.getHeading();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getNumberSign().hashCode();
        result = 31 * result + getHeading().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("destinationSign", getDestinationSign())
                .append("numberSign", getNumberSign())
                .append("workingDays", getWorkingDays())
                .append("heading", getHeading())
                .append("shape", getShape())
                .append("stops", getStops())
                .append("farePrice", getFarePrice())
                .append("isCircular", isCircular())
                .append("olhovivoId", getOlhovivoId())
                .append("gtfsId", getGtfsId())
                .toString();
    }
}
