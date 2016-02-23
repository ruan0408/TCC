package com.intellij.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusNow extends Bus {

    private String predictedArrivalTime;

    @JsonCreator
    protected BusNow(@JsonProperty("p") String prefixNumber,
                     @JsonProperty("a") boolean wheelChairCapable,
                     @JsonProperty("py") double latitude,
                     @JsonProperty("px") double longitude,
                     @JsonProperty("t") String predictedArrivalTime) {

        super(prefixNumber, wheelChairCapable, latitude, longitude);
        this.predictedArrivalTime = predictedArrivalTime;
    }

    /**
     * @return The predicted arrival time of this bus at the related bus stop.
     */
    public String getPredictedArrivalTime() {
        return predictedArrivalTime;
    }
}
