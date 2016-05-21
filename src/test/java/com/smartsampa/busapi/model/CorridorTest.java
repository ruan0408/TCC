package com.smartsampa.busapi.model;

import com.smartsampa.busapi.impl.BusAPI;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ruan0408 on 29/04/2016.
 */
public class CorridorTest {

    @BeforeClass
    public static void setUp() throws Exception {
        BusAPITestUtils.setUpDataSources();
    }

    @Test
    public void testGetAllCorridors() throws Exception {
        List<Corridor> corridors = BusAPI.getAllCorridors();
        assertTrue(corridors.size() > 0);
    }

    @Test
    public void testGetId() throws Exception {
        Corridor corridor =  BusAPI.getCorridorByTerm("campo limpo");
        assertEquals(8, corridor.getId());
    }

    @Test
    public void testGetCodCot() throws Exception {
        Corridor corridor = BusAPI.getCorridorByTerm("campo limpo");
        assertEquals(0, corridor.getCodCot());
    }

    @Test
    public void testGetName() throws Exception {
        Corridor corridor = BusAPI.getCorridorByTerm("campo limpo");
        assertEquals("Campo Limpo", corridor.getName());
    }

    @Test
    public void testGetStops() throws Exception {
        Corridor corridor = BusAPI.getCorridorByTerm("campo limpo");
        List<Stop> stops = corridor.getStops();

        assertFalse(stops.isEmpty());
        assertTrue(stops.stream().allMatch(stop -> stop.getName() != null));
    }
}