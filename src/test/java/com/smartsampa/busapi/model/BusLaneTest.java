package com.smartsampa.busapi.model;

import com.smartsampa.busapi.impl.BusAPI;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

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
        assertFalse(lanes.isEmpty());
    }

    @Test
    public void testGetBusLanesByTerm() throws Exception {
        List<BusLane> lanes = BusAPI.getBusLanesByTerm("haia");
        assertFalse(lanes.isEmpty());
    }

    @Test
    public void testGetName() throws Exception {
        assertTrue(allBusLanes.stream().allMatch(lane -> lane.getName() != null));
    }

    @Test
    public void testGetAddress() throws Exception {
        assertTrue(allBusLanes.stream().allMatch(lane -> lane.getAddress() != null));
    }

    @Test
    public void testGetAddressStart() throws Exception {
        assertTrue(allBusLanes.stream().allMatch(lane -> lane.getAddressStart() != null));
    }

    @Test
    public void testGetAddressEnd() throws Exception {
        assertTrue(allBusLanes.stream().allMatch(lane -> lane.getAddressEnd() != null));
    }

    @Test
    public void testGetHeading() throws Exception {
        assertTrue(allBusLanes.stream().allMatch(lane -> lane.getHeading() != null));
    }

    @Test
    public void testGetDistrict() throws Exception {
        assertTrue(allBusLanes.stream().allMatch(lane -> lane.getDistrict() != null));
    }

    @Test
    public void testGetImplantationDate() throws Exception {
        assertTrue(allBusLanes.stream().allMatch(lane -> lane.getImplantationDate() != null));
    }

    @Test
    public void testGetStartWorkingTime() throws Exception {
        assertTrue(allBusLanes.stream().allMatch(lane -> lane.getStartWorkingTime() != null));
    }

    @Test
    public void testGetEndWorkingTime() throws Exception {
        assertTrue(allBusLanes.stream().allMatch(lane -> lane.getEndWorkingTime() != null));
    }

    @Test
    public void testGetStreetSide() throws Exception {
        assertTrue(allBusLanes.stream().allMatch(lane -> lane.getStreetSide() != null));
    }

    @Test
    public void testGetAmountWorkingHours() throws Exception {
        assertTrue(allBusLanes.stream().allMatch(lane -> lane.getAmountWorkingHours() != 0));
    }

    @Test
    public void testGetRegionCode() throws Exception {
        assertTrue(allBusLanes.stream().allMatch(lane -> lane.getRegionCode() != 0));
    }

    @Test
    public void testGetSizeInMeters() throws Exception {
        assertTrue(allBusLanes.stream().allMatch(lane -> lane.getSizeInMeters() != 0));
    }

    @Test
    public void testGetShape() throws Exception {
        assertTrue(allBusLanes.stream().allMatch(lane -> lane.getShape().getPoints() != null));
    }
}