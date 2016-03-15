package com.intellij.busapi;

import org.apache.commons.lang.text.StrBuilder;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class PredictedBus extends Bus {

    private String predictedArrival;

    public String getPredictedArrival() {return predictedArrival;}
    public String setPredictedArrival(String arrival) {return predictedArrival = arrival;}

    @Override
    public String toString() {
        StrBuilder builder = new StrBuilder();
        builder.appendln(super.toString());
        builder.append(predictedArrival);
        return builder.toString();
    }
}
