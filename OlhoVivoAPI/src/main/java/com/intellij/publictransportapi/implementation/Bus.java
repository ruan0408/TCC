package com.intellij.publictransportapi.implementation;

import org.apache.commons.lang.text.StrBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class Bus {

    private String prefix;
    private boolean wheelChairCapable;
    private Point location;

    protected Bus(com.intellij.olhovivoapi.Bus bus) {
        prefix = bus.getPrefixNumber();
        wheelChairCapable = bus.isWheelChairCapable();
        location = new Point(bus.getLatitude(), bus.getLongitude());
    }

    public String getPrefix() {return prefix;}

    public boolean isWheelChairCapable() {return false;}

    public Point getLocation() {return null;}

    protected static List<Bus> convert(com.intellij.olhovivoapi.Bus[] buses) {
        List<Bus> list = new ArrayList<>(buses.length);
        for (int i = 0; i < buses.length; i++) {
            list.add(new Bus(buses[i]));
        }
        return list;
    }

    @Override
    public String toString() {
        StrBuilder builder = new StrBuilder();
        builder.appendln("prefix: "+prefix);
        builder.appendln("wheelChairCapable: "+wheelChairCapable);
        builder.appendln("location: "+location.toString());
        return builder.toString();
    }
}
