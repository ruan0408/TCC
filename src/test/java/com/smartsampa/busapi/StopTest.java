package com.smartsampa.busapi;

import com.smartsampa.utils.Point;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.smartsampa.busapi.BusAPITestUtils.isAfter4amAndBeforeMidnight;

/**
 * Created by ruan0408 on 16/04/2016.
 */
public class StopTest {

    @BeforeClass
    public static void setUp() throws Exception {
        BusAPITestUtils.setUpDataSources();
    }

    @Test
    public void testSearchStopsByTerm() throws Exception {
        Set<Stop> stops = BusAPI.getStopsByTerm("campanella");
        Assert.assertFalse(stops.isEmpty());
    }

    @Test
    public void testGetStopById() throws Exception {
        Stop campanella = BusAPI.getStopById(360004869);
        Assert.assertTrue(campanella.getName() != null);
    }

    @Test
    public void testGetTrips() throws Exception {
        Stop campanella = BusAPI.getStopById(360004869);
        Set<Trip> trips = campanella.getTrips();

        Assert.assertFalse(trips.isEmpty());
        Assert.assertTrue(trips.stream().anyMatch(t -> t.getNumberSign().equals("2732-10")));
    }

    @Test
    public void testGetPredictionsPerTrip() throws Exception {
        Assume.assumeTrue(isAfter4amAndBeforeMidnight());

        Stop vitalBrasil = BusAPI.getStopById(1211364);
        Map<Trip, List<PredictedBus>> predictions = vitalBrasil.getPredictionsPerTrip();

        Assume.assumeNotNull(predictions);
        Assert.assertTrue(predictions.size() > 0);
        Assert.assertTrue(predictions.values().stream().anyMatch(buses -> !buses.isEmpty()));
    }

    @Test
    public void testGetPredictedBusesOfTrip() throws Exception {
        Assume.assumeTrue(isAfter4amAndBeforeMidnight());

        Stop campanella = BusAPI.getStopById(360004869);

        Trip alvimPerua = Mockito.mock(Trip.class);
        Mockito.when(alvimPerua.getOlhovivoId()).thenReturn(33715);

        List<PredictedBus> predictions = campanella.getPredictedBusesOfTrip(alvimPerua);

        Assume.assumeNotNull(predictions);
        Assert.assertFalse(predictions.isEmpty());
    }

    @Test
    public void testGetId() throws Exception {
        Stop fea = BusAPI.getStopById(120010356);
        Assert.assertEquals(120010356, fea.getId().intValue());
    }

    @Test
    public void testGetName() throws Exception {
        Stop fea = BusAPI.getStopById(120010356);
        Assert.assertEquals("Fea", fea.getName());
    }

    @Test
    public void testGetAddress() throws Exception {
        Stop fea = BusAPI.getStopById(120010356);
        Assert.assertNull(fea.getAddress());
    }

    @Test
    public void testGetReference() throws Exception {
        Stop fea = BusAPI.getStopById(120010356);
        Assert.assertEquals("Av. Prof. Luciano Gualberto Ref.: R V/ Av Professor Lucio Martins Rodrigues",
                fea.getReference());
    }

    @Test
    public void testGetLocation() throws Exception {
        Stop fea = BusAPI.getStopById(120010356);
        Assert.assertEquals(new Point(-23.558951,-46.729803), fea.getLocation());
    }
}