package com.smartsampa.olhovivoapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.smartsampa.busapi.PredictedBus;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusNow extends OlhovivoBus implements PredictedBus {

    public String predictedArrivalTime;

    @JsonCreator
    protected BusNow(@JsonProperty("p") String prefixNumber,
                     @JsonProperty("a") boolean wheelChairCapable,
                     @JsonProperty("py") double latitude,
                     @JsonProperty("px") double longitude,
                     @JsonProperty("t") String predictedArrivalTime) {

        super(prefixNumber, wheelChairCapable, latitude, longitude);
        this.predictedArrivalTime = predictedArrivalTime;
    }

    public String getPredictedArrivalTime() {
        return predictedArrivalTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BusNow{");
        sb.append(super.toString());
        sb.append("predictedArrivalTime='").append(predictedArrivalTime).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
