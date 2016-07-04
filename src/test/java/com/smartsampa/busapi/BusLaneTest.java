package com.smartsampa.busapi;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * Created by ruan0408 on 30/04/2016.
 */
public class BusLaneTest {

    private static List<BusLane> allBusLanes;

    @BeforeClass
    public static void setUp() throws Exception {
        BusAPITestUtils.setUpDataSources();
        allBusLanes = BusAPI.getAllBusLanes();
    }

    @Test
    public void testGetAllBusLanes() throws Exception {
        List<BusLane> lanes = BusAPI.getAllBusLanes();
        Assert.assertFalse(lanes.isEmpty());
    }

    @Test
    public void testGetBusLanesByTerm() throws Exception {
        List<BusLane> lanes = BusAPI.getBusLanesByTerm("haia");
        Assert.assertFalse(lanes.isEmpty());
    }

    @Test
    public void testGetName() throws Exception {
        Assert.assertTrue(allBusLanes.stream().allMatch(lane -> lane.getName() != null));
    }

    @Test
    public void testGetAddress() throws Exception {
        Assert.assertTrue(allBusLanes.stream().allMatch(lane -> lane.getAddress() != null));
    }

    @Test
    public void testGetAddressStart() throws Exception {
        Assert.assertTrue(allBusLanes.stream().allMatch(lane -> lane.getAddressStart() != null));
    }

    @Test
    public void testGetAddressEnd() throws Exception {
        Assert.assertTrue(allBusLanes.stream().allMatch(lane -> lane.getAddressEnd() != null));
    }

    @Test
    public void testGetHeading() throws Exception {
        Assert.assertTrue(allBusLanes.stream().allMatch(lane -> lane.getHeading() != null));
    }

    @Test
    public void testGetDistrict() throws Exception {
        Assert.assertTrue(allBusLanes.stream().allMatch(lane -> lane.getDistrict() != null));
    }

    @Test
    public void testGetImplantationDate() throws Exception {
        Assert.assertTrue(allBusLanes.stream().allMatch(lane -> lane.getImplantationDate() != null));
    }

    @Test
    public void testGetStartWorkingTime() throws Exception {
        Assert.assertTrue(allBusLanes.stream().allMatch(lane -> lane.getStartWorkingTime() != null));
    }

    @Test
    public void testGetEndWorkingTime() throws Exception {
        Assert.assertTrue(allBusLanes.stream().allMatch(lane -> lane.getEndWorkingTime() != null));
    }

    @Test
    public void testGetStreetSide() throws Exception {
        Assert.assertTrue(allBusLanes.stream().allMatch(lane -> lane.getStreetSide() != null));
    }

    @Test
    public void testGetAmountWorkingHours() throws Exception {
        Assert.assertTrue(allBusLanes.stream().allMatch(lane -> lane.getAmountWorkingHours() != 0));
    }

    @Test
    public void testGetRegionCode() throws Exception {
        Assert.assertTrue(allBusLanes.stream().allMatch(lane -> lane.getRegionCode() != 0));
    }

    @Test
    public void testGetSizeInMeters() throws Exception {
        Assert.assertTrue(allBusLanes.stream().allMatch(lane -> lane.getSizeInMeters() != 0));
    }

    @Test
    public void testGetShape() throws Exception {
        Assert.assertTrue(allBusLanes.stream().allMatch(lane -> lane.getShape().getPoints() != null));
    }
}