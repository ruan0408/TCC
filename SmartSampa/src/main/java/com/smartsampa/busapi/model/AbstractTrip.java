package com.smartsampa.busapi.model;

import com.smartsampa.busapi.impl.BusAPI;
import com.smartsampa.busapi.impl.BusAPIManager;
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

    @Override
    public Map<Stop, List<PredictedBus>> getAllPredictions() {
        Map<AbstractStop, List<PredictedBus>> predictions =
                BusAPIManager.olhovivo.getPredictionsOfTrip(getOlhovivoId());

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

    public boolean containsTerm(String term) {
        return StringUtils.containsIgnoreCase(getDestinationSign(), term) ||
                StringUtils.containsIgnoreCase(getNumberSign(), term);
    }

    @Override
    public void merge(Mergeable mergeable) {
        if (mergeable == null) return;

        Trip other = (Trip) mergeable;
        if (getDestinationSign() == null) setDestinationSign(other.getDestinationSign());
        if (getFarePrice() == null) setFarePrice(other.getFarePrice());
        if (getGtfsId() == null) setGtfsId(other.getGtfsId());
        if (getHeading() == null) setHeading(other.getHeading());
        if (getNumberSign() == null) setNumberSign(other.getNumberSign());
        if (getOlhovivoId() == null) setOlhovivoId(other.getOlhovivoId());
        if (getShape() == null) setShape(other.getShape());
        if (getStops() == null) setStops(other.getStops());
        if (getWorkingDays() == null) setWorkingDays(other.getWorkingDays());
        if (isCircular() == null) setCircular(other.isCircular());
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
