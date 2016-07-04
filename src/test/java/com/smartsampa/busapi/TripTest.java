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
import java.util.concurrent.ExecutionException;

import static com.smartsampa.busapi.BusAPITestUtils.isAfter4amAndBeforeMidnight;
import static com.smartsampa.busapi.Heading.MAIN_TERMINAL;
import static com.smartsampa.busapi.Heading.SECONDARY_TERMINAL;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * Created by ruan0408 on 13/04/2016.
 */
public class TripTest {

    @BeforeClass
    public static void setUp() {
        BusAPITestUtils.setUpDataSources();
    }

    @Test
    public void testGetTripsWithHyphenatedName() {
        Set<Trip> trips = BusAPI.getTripsByTerm("Shopping - D");
        Assert.assertTrue(trips.size() == 1);
        Assert.assertTrue(trips.stream().anyMatch(trip -> trip.getDestinationSign().equals("Shopping - D")));
    }

    @Test
    public void testGetTripsByOneTerm() throws Exception {
        Set<Trip> trips = BusAPI.getTripsByTerm("regina");
        Assert.assertTrue(trips.size() == 1);
        Assert.assertTrue(trips.stream().anyMatch(t -> t.getNumberSign().equals("2732-10") &&
                containsIgnoreCase( t.getDestinationSign(), "vila regina") &&
                t.getHeading() == SECONDARY_TERMINAL));
    }

    @Test
    public void testGetTripByTwoTerms() throws Exception {
        Set<Trip> trips = BusAPI.getTripsByTerm("vila regina");
        Assert.assertTrue(trips.size() == 1);
        Assert.assertTrue(trips.stream().anyMatch(t -> t.getNumberSign().equals("2732-10") &&
                containsIgnoreCase( t.getDestinationSign(), "vila regina") &&
                t.getHeading() == SECONDARY_TERMINAL));
    }

    @Test
    public void testGetTrip() throws Exception {
        Trip bonifacio = BusAPI.getTrip("273L-10", SECONDARY_TERMINAL);
        Assert.assertTrue(equalsIgnoreCase(bonifacio.getDestinationSign(), "Metrô Artur Alvim"));
        Assert.assertTrue(contains(bonifacio.getNumberSign(), "273L-10"));
        Assert.assertEquals(bonifacio.getHeading(), Heading.SECONDARY_TERMINAL);
    }

    @Test
    public void testGetIdMainTerminal() throws Exception {
        Trip vilaJacui = BusAPI.getTrip("2730-10", MAIN_TERMINAL);
        Assert.assertEquals("2730-10-2", vilaJacui.getId());
    }

    @Test
    public void testGetIdSecondaryTerminal() throws Exception {
        Trip pracaSe = BusAPI.getTrip("7411-10", SECONDARY_TERMINAL);
        Assert.assertEquals("7411-10-1", pracaSe.getId());
    }

    @Test
    public void testGetStops() throws Exception {
        Trip butanta8022 = BusAPI.getTrip("8022-10", MAIN_TERMINAL);
        List<Stop> stops = butanta8022.getStops();
        Assert.assertFalse(stops.isEmpty());
    }

    @Test
    public void testGetWorkingDays() throws Exception {
        Trip usp8012 = BusAPI.getTrip("8012-10", SECONDARY_TERMINAL);
        Assert.assertEquals("USD", usp8012.getWorkingDays());
    }

    @Test
    public void testGetShape() throws Exception {
        Trip usp8012 = BusAPI.getTrip("8012-10", SECONDARY_TERMINAL);
        Point[] points = usp8012.getShape().getPoints();
        Assume.assumeNotNull(points);
        Assert.assertEquals(419, points.length);
        Assert.assertNotNull(usp8012.getShape().getTraveledDistances());
    }

    @Test
    public void testGetAllRunningBuses() throws ExecutionException {
        Trip alvimPerua = BusAPI.getTrip("2732-10", MAIN_TERMINAL);
        Set<Bus> buses = alvimPerua.getAllRunningBuses();
        Assert.assertFalse(buses.isEmpty());
        Assert.assertTrue(buses.size() > 0);
    }

    @Test
    public void testGetFarePrice() throws Exception {
        Trip penha = BusAPI.getTrip("263J-10", MAIN_TERMINAL);
        Assert.assertEquals(3.80, penha.getFarePrice(), 0.0001);
    }

    @Test
    public void testGetDepartureIntervalAtTime() throws Exception {
        Trip barroBranco = BusAPI.getTrip("407L-10", MAIN_TERMINAL);
        Assert.assertEquals(600, barroBranco.getDepartureIntervalInSecondsAtTime("14:30"));
    }

    @Test
    public void testGetDepartureIntervalNow() throws Exception {
        Trip penha = BusAPI.getTrip("263J-10", MAIN_TERMINAL);
        Assert.assertTrue(penha.getDepartureIntervalInSecondsNow() > 0);
    }

    //TODO test that the stops are complete(find a trip that has a stop which olhovivo knows its address)
    @Test
    public void testGetPredictionsPerStop() throws Exception {
        Assume.assumeTrue(isAfter4amAndBeforeMidnight());
        Trip alvim = BusAPI.getTrip("273L-10", SECONDARY_TERMINAL);
        Map<Stop, List<PredictedBus>> predictions = alvim.getPredictionsPerStop();
        Assume.assumeNotNull(predictions);
        Assert.assertTrue(predictions.size() > 0);
        Assert.assertTrue(predictions.values().stream().anyMatch(buses -> !buses.isEmpty()));
    }

    @Test
    public void testGetPredictionsAtStop() throws Exception {
        Assume.assumeTrue(isAfter4amAndBeforeMidnight());

        Trip alvimPerua = BusAPI.getTrip("2732-10", MAIN_TERMINAL);
        Stop campanella = Mockito.mock(Stop.class);
        Mockito.when(campanella.getId()).thenReturn(360004869);
        List<PredictedBus> buses = alvimPerua.getPredictionsAtStop(campanella);

        Assert.assertFalse(buses.isEmpty());
    }
}