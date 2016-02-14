package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusNow extends Bus {

    private String predictedArrivalTime;

    @JsonCreator
    public BusNow(@JsonProperty("p") String prefixNumber,
                  @JsonProperty("a") boolean wheelChairCapable,
                  @JsonProperty("py") double latitude,
                  @JsonProperty("px") double longitude,
                  @JsonProperty("t") String predictedArrivalTime) {

        super(prefixNumber, wheelChairCapable, latitude, longitude);
        this.predictedArrivalTime = predictedArrivalTime;
    }
}
