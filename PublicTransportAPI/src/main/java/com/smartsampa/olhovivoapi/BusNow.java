package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

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

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("predictedArrivalTime", predictedArrivalTime)
                .toString();
    }
}
