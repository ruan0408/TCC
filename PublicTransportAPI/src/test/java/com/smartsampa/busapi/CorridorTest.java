package com.smartsampa.busapi;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * Created by ruan0408 on 21/02/2016.
 */
public class CorridorTest {

    private Corridor campoLimpo = new Corridor(8, "Campo Limpo");

    @BeforeClass
    public static void setUp() throws Exception {
        BusAPITestUtils.setUpDataSources();
    }

    @Test
    public void testGetAllStops() throws Exception {
        List<Stop> allStops = campoLimpo.getAllStops();
        Assert.assertEquals(33, allStops.size());
    }

    @Test
    public void testGetAllCorridors() throws Exception {
        List<Corridor> allCorridors = Corridor.getAllCorridors();
        Assert.assertEquals(7, allCorridors.size());
    }
}