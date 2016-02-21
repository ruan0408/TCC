package com.intellij.publictransportapi.implementation;

import com.intellij.olhovivoapi.BusCorridor;
import org.apache.commons.lang.text.StrBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class Corridor {

    private int id;
    private String name;

    public Corridor(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getCode() {return id;}

    public String getName() {return name;}

    public List<Stop> getAllStops() {
        return Stop.convert(API.getStopsByCorridor(id));
    }

    protected static List<Corridor> convert(BusCorridor[] corridors) {
        List<Corridor> list = new ArrayList<>(corridors.length);

        for (int i = 0; i < corridors.length; i++)
            list.add(Corridor.buildFromBusCorridor(corridors[i]));

        return list;
    }

    private static Corridor buildFromBusCorridor(BusCorridor corridor) {
        return new Corridor(corridor.getCode(),corridor.getName());
    }

    @Override
    public String toString() {
        StrBuilder builder = new StrBuilder();
        builder.appendln("id: "+id);
        builder.appendln("name: "+name);
        return builder.toString();
    }
}
