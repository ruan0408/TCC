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

    public String getPrefix() {return prefix;}

    public boolean isWheelChairCapable() {return false;}

    public Point getLocation() {return null;}

    public void setPrefix(String prefix) {this.prefix = prefix;}

    public void setWheelChairCapable(boolean wheelChairCapable) {this.wheelChairCapable = wheelChairCapable;}

    public void setLocation(Point location) {this.location = location;}

    protected static <T extends Bus, K extends com.intellij.olhovivoapi.Bus>
    List<T> convert(K[] buses, Class<T> tClass) {
        List<T> list = new ArrayList<>(buses.length);

        for (int i = 0; i < buses.length; i++)
            list.add(Bus.buildFromAPIBus(buses[i], tClass));

        return list;
    }

    protected static <T extends Bus> T
    buildFromAPIBus(com.intellij.olhovivoapi.Bus bus, Class<T> tClass) {
        T newBus = null;
        try {
            newBus = tClass.newInstance();
            newBus.setPrefix(bus.getPrefixNumber());
            newBus.setWheelChairCapable(bus.isWheelChairCapable());
            newBus.setLocation(new Point(bus.getLatitude(), bus.getLongitude()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newBus;
    }

    @Override
    public String toString() {
        StrBuilder builder = new StrBuilder();
        builder.appendln("prefix: "+prefix);
        builder.appendln("wheelChairCapable: "+wheelChairCapable);
        builder.append("location: "+location.toString());
        return builder.toString();
    }
}
