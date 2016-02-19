package com.intellij.publictransportapi.implementation;

/**
 * Created by ruan0408 on 19/02/2016.
 */
public class Point {

    private double py;
    private double px;

    public Point(double py, double px) {
        this.py = py;
        this.px = px;
    }

    @Override
    public String toString() {
        return "("+py+","+px+")";
    }
}
