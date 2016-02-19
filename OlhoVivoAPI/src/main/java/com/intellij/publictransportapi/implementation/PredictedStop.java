package com.intellij.publictransportapi.implementation;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class PredictedStop extends Stop{

    public PredictedStop(int id, String name, String address, Point location) {
        super(id, name, address, location);
    }

    public String getPredictedArrival() {
        return null;
    }
}
