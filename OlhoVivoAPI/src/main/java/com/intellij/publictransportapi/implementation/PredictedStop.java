package com.intellij.publictransportapi.implementation;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class PredictedStop extends Stop{

    public PredictedStop(int id, String name, Point location) {
        super(id, name, location);
    }

    public String getPredictedArrival() {return null;}
}
