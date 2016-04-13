package com.smartsampa.model;

import com.smartsampa.busapi.BusAPITestUtils;
import com.smartsampa.utils.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

/**
 * Created by ruan0408 on 13/04/2016.
 */
public class TripTest {

    @BeforeClass
    public static void setUp() {
        BusAPITestUtils.setUpDataSources();
    }

    @Test
    public void testGetTripsByTerm() throws Exception {
        Set<Trip> trips = Trip.getTripsByTerm("regina");
        Utils.printCollection(trips);
    }
}