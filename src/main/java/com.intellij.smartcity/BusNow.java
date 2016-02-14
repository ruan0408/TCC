package com.intellij.smartcity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ruan0408 on 12/02/2016.
 */
public class BusNow extends Bus {

    private String predictedArrivalTime;

    @JsonProperty("t")
    public String getPredictedArrivalTime() {return predictedArrivalTime;}
}
