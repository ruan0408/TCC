package com.smartsampa.busapi;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public abstract class Trip {

    private String destinationSign;
    private String numberSign;
    private String workingDays;
    private Heading heading;
    private Shape shape;
    private Double farePrice;
    private Boolean circular;
    private Integer olhovivoId;
    private String gtfsId;

    public static Trip emptyTrip() { return NullTrip.getInstance(); }

    public final String getId() { return getNumberSign()+"-"+getHeading().getIntFromHeading(); }

    public String getNumberSign() {return numberSign;}
    public void setNumberSign(String numberSign) { this.numberSign = numberSign; }

    public String getDestinationSign() {return destinationSign;}
    public void setDestinationSign(String destinationSign) { this.destinationSign = destinationSign;}

    public Heading getHeading() {return heading;}
    public void setHeading(Heading heading) { this.heading = heading; }

    public String getWorkingDays() {return workingDays;}
    public void setWorkingDays(String workingDays) { this.workingDays = workingDays; }

    public Shape getShape() {return shape;}
    public void setShape(Shape shape) { this.shape = shape; }

    public Double getFarePrice() {return farePrice;}
    public void setFarePrice(Double farePrice) { this.farePrice = farePrice; }

    public Boolean isCircular() {return circular;}
    public void setCircular(Boolean circular) { this.circular = circular; }

    public Integer getOlhovivoId() {return olhovivoId;}
    public void setOlhovivoId(Integer olhovivoId) { this.olhovivoId = olhovivoId; }

    public String getGtfsId() {return gtfsId;}
    public void setGtfsId(String gtfsId) { this.gtfsId = gtfsId; }

    public int getDepartureIntervalInSecondsAtTime(String hhmm) { return -1; }

    public int getDepartureIntervalInSecondsNow() {return -1;}

    public boolean containsTerm(String term) {
        return StringUtils.containsIgnoreCase(getDestinationSign(), term) ||
                StringUtils.containsIgnoreCase(getNumberSign(), term);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Trip)) return false;

        Trip that = (Trip) o;
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
                .append("farePrice", getFarePrice())
                .append("circular", isCircular())
                .append("olhovivoId", getOlhovivoId())
                .append("gtfsId", getGtfsId())
                .toString();
    }
}
