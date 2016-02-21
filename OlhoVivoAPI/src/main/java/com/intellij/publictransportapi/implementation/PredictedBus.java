package com.intellij.publictransportapi.implementation;

import com.intellij.olhovivoapi.BusNow;
import org.apache.commons.lang.text.StrBuilder;

import java.util.List;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class PredictedBus extends Bus {

    private String predictedArrival;

    public String getPredictedArrival() {return predictedArrival;}
    public String setPredictedArrival(String arrival) {return predictedArrival = arrival;}

    protected static List<PredictedBus> convert(BusNow[] buses) {

        List<PredictedBus> list = Bus.convert(buses, PredictedBus.class);
        for (int i = 0; i < buses.length; i++)
            list.get(i).setPredictedArrival(buses[i].getPredictedArrivalTime());

        return list;
    }

    @Override
    public String toString() {
        StrBuilder builder = new StrBuilder();
        builder.appendln(super.toString());
        builder.append(predictedArrival);
        return builder.toString();
    }
}
