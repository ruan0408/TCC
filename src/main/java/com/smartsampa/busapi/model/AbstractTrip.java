package com.smartsampa.busapi.model;

import com.smartsampa.busapi.impl.BusAPI;
import com.smartsampa.olhovivoapi.OlhoVivoAPI;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public abstract class AbstractTrip implements Trip {

    private static OlhoVivoAPI olhoVivoAPI = Provider.getOlhovivoAPI();

    private String destinationSign;
    private String numberSign;
    private String workingDays;
    private Heading heading;
    private Shape shape;
    private Double farePrice;
    private Boolean isCircular;
    private Integer olhovivoId;
    private String gtfsId;

    @Override
    public Map<Stop, List<PredictedBus>> getPredictionsPerStop() {
        Map<AbstractStop, List<PredictedBus>> predictions =
                olhoVivoAPI.getPredictionsOfTrip(getOlhovivoId());

        return replaceStopsByCompleteStop(predictions);
    }

    private Map<Stop, List<PredictedBus>> replaceStopsByCompleteStop(Map<AbstractStop, List<PredictedBus>> predictions) {
        Map<Stop, List<PredictedBus>> result = new HashMap<>(predictions.size());
        predictions.keySet().stream().forEach(oldStop -> {
            Stop newStop = BusAPI.getStopById(oldStop.getId());
            result.put(newStop, predictions.get(oldStop));
        });
        return result;
    }

    @Override
    public List<PredictedBus> getPredictionsAtStop(Stop stop) {
        return olhoVivoAPI.getPredictionsOfTripAtStop(getOlhovivoId(), stop.getId());
    }

    @Override
    public Set<Bus> getAllRunningBuses() {return olhoVivoAPI.getAllRunningBusesOfTrip(getOlhovivoId());}

    @Override
    public List<Stop> getStops() {
        return BusAPI.getStopsFrom(this);
    }

    @Override
    public void merge(Mergeable mergeable) {
        if (mergeable == null) return;

        Trip other = (Trip) mergeable;
        if (getDestinationSign() == null) destinationSign = other.getDestinationSign();
        if (getFarePrice() == null) farePrice = other.getFarePrice();
        if (getGtfsId() == null) gtfsId = other.getGtfsId();
        if (getHeading() == null) heading = other.getHeading();
        if (getNumberSign() == null) numberSign = other.getNumberSign();
        if (getOlhovivoId() == null) olhovivoId = other.getOlhovivoId();
        if (getShape() == null) shape = other.getShape();
        if (getWorkingDays() == null) workingDays = other.getWorkingDays();
        if (isCircular() == null) isCircular = other.isCircular();
    }

    @Override
    public String getId() {
        return getNumberSign()+"-"+Heading.getIntFromHeading(getHeading());
    }

    @Override
    public Shape getShape() {return shape;}

    @Override
    public int getDepartureIntervalInSecondsAtTime(String hhmm) { return -1; }

    @Override
    public int getDepartureIntervalInSecondsNow() {return -1;}

    @Override
    public String getNumberSign() {return numberSign;}

    @Override
    public Heading getHeading() {return heading;}

    @Override
    public String getDestinationSign() {return destinationSign;}

    @Override
    public String getWorkingDays() {return workingDays;}

    @Override
    public Double getFarePrice() {return farePrice;}

    @Override
    public Boolean isCircular() {return isCircular;}

    @Override
    public Integer getOlhovivoId() {return olhovivoId;}

    @Override
    public String getGtfsId() {return gtfsId;}

    public boolean containsTerm(String term) {
        return StringUtils.containsIgnoreCase(getDestinationSign(), term) ||
                StringUtils.containsIgnoreCase(getNumberSign(), term);
    }

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
