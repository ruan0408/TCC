package com.smartsampa.busapi;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class PredictedBus extends Bus {

    private String predictedArrival;

    public String getPredictedArrival() {return predictedArrival;}
    public String setPredictedArrival(String arrival) {return predictedArrival = arrival;}

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("predictedArrival", predictedArrival)
                .toString();
    }
}
