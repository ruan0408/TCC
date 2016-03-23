package com.smartsampa.busapi;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by ruan0408 on 21/03/2016.
 */
public class BusLaneTest {

    @Test
    public void testGetAllBusLanes() throws Exception {
        List<BusLane> busLanes = BusLane.getAllBusLanes();
        Assert.assertNotNull(busLanes);
        Assert.assertFalse(busLanes.isEmpty());
    }

    @Test
    public void testSearchByTerm() throws Exception {
        List<BusLane> busLanes = BusLane.searchByTerm("FAUSTOLO");
        Assert.assertNotNull(busLanes);
        Assert.assertEquals(1, busLanes.size());
    }

    @Test
    public void testGetShape() throws Exception {
        List<BusLane> busLanes = BusLane.getAllBusLanes();
        Assert.assertTrue(busLanes.stream()
                .allMatch(b -> (b.getShape().getPoints()) != null));

    }
}
