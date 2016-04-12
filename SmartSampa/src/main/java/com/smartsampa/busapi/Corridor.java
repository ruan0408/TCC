package com.smartsampa.busapi;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class Corridor {

    private int id;
    private String name;

    private CorridorFacade adapter;

    public Corridor(int id, String name) {
        this.id = id;
        this.name = name;
        adapter = new CorridorFacade();
    }

    protected static List<Corridor> getAllCorridors() {
        return CorridorFacade.getAllCorridors();
    }

    public int getCode() {return id;}

    public String getName() {return name;}

    public List<Stop> getAllStops() {
        return adapter.getAllStopsFromCorridor(id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("adapter", adapter)
                .toString();
    }
}
