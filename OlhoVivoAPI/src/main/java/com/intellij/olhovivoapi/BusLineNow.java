package com.intellij.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 12/02/2016.
 */

public class BusLineNow {

    private BusLine busLine;
    private int numberOfVehicles;
    private BusNow[] vehicles;

    @JsonCreator
    protected BusLineNow(@JsonProperty("cl") int busLineCode,
                         @JsonProperty("c") String busLineDestinationSign,
                         @JsonProperty("sl") int heading,
                         @JsonProperty("lt0") String destinationSignMTST,
                         @JsonProperty("lt1") String destinationSignSTMT,
                         @JsonProperty("qv") int numberOfVehicles,
                         @JsonProperty("vs") BusNow[] vehicles) {

        busLine = new BusLine(busLineCode, busLineDestinationSign, heading, destinationSignMTST,destinationSignSTMT);
        this.numberOfVehicles = numberOfVehicles;
        this.vehicles = vehicles;
    }

    /**
     * @return The code of this bus line.
     */
    public int getCode() {
        return busLine.getCode();
    }

    /**
     * @return True if this bus line doesn't have a secondary terminal.
     * False otherwise.
     */
    public boolean isCircular() {
        return busLine.isCircular();
    }

    /**
     * @return The destination sign of this bus line.
     */
    public String getDestinationSign() {
        return busLine.getNumberSign();
    }

    /**
     * @return The heading of this bus line. 1 if the line goes from
     * the secondary terminal to the main terminal and 2 if the
     * line runs from the main terminal to the secondary terminal.
     */
    public int getHeading() {
        return busLine.getHeading();
    }

    /**
     * @return Second part of the non-internal line code.
     * BASE (10), ATENDIMENTO (21, 23, 32, 41).
     * e.g.: In the line 2732-10, the type is 10.
     */
    public int getType() {
        return busLine.getType();
    }

    /**
     * @return General information about this line.
     */
    public String getInfo() {
        return busLine.getInfo();
    }

    /**
     * @return The name displayed on the billboard when the buses on
     * this line are heading towards the secondary terminal.
     */
    public String getDestinationSignMTST() {
        return busLine.getDestinationSignMTST();
    }

    /**
     * @return The name displayed on the billboard when the buses on
     * this line are heading towards the main terminal.
     */
    public String getDestinationSignSTMT() {
        return busLine.getDestinationSignSTMT();
    }

    /**
     * @return The number of vehicles on this line right now.
     */
    public int getNumberOfVehicles() {
        return numberOfVehicles;
    }

    /**
     * @return The vehicles on this line.
     */
    public BusNow[] getVehicles() {
        return vehicles;
    }
}
