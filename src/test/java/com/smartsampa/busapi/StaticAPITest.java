package com.smartsampa.busapi;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static com.smartsampa.busapi.Heading.SECONDARY_TERMINAL;
import static org.apache.commons.lang3.StringUtils.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by ruan0408 on 16/07/2016.
 */
public class StaticAPITest {

    private static StaticAPI staticAPI;

    @BeforeClass
    public static void setUp() {
        BusAPITestUtils.setUpDataSources();
        staticAPI = new StaticAPI();
    }

    @Test
    public void testGetTripsWithHyphenatedName() {
        Set<Trip> trips = staticAPI.getTripsByTerm("Shopping - D");
        assertTrue(trips.size() == 1);
        assertTrue(trips.stream().anyMatch(trip -> trip.getDestinationSign().equals("Shopping - D")));
    }

    @Test
    public void testGetTripsByOneTerm() throws Exception {
        Set<Trip> trips = staticAPI.getTripsByTerm("regina");
        assertTrue(trips.size() == 1);
        assertTrue(trips.stream().anyMatch(t -> t.getNumberSign().equals("2732-10") &&
                containsIgnoreCase( t.getDestinationSign(), "vila regina") &&
                t.getHeading() == SECONDARY_TERMINAL));
    }

    @Test
    public void testGetTripByTwoTerms() throws Exception {
        Set<Trip> trips = staticAPI.getTripsByTerm("vila regina");
        assertTrue(trips.size() == 1);
        assertTrue(trips.stream().anyMatch(t -> t.getNumberSign().equals("2732-10") &&
                containsIgnoreCase( t.getDestinationSign(), "vila regina") &&
                t.getHeading() == SECONDARY_TERMINAL));
    }

    @Test
    public void getStopsByTerm() throws Exception {
        Set<Stop> stops = staticAPI.getStopsByTerm("campanella");
        assertFalse(stops.isEmpty());
    }

    @Test
    public void getStopById() throws Exception {
        Stop campanella = staticAPI.getStopById(360004869);
        assertTrue(campanella.getName() != null);
    }

    @Test
    public void getTripsFromStop() throws Exception {
        Stop campanella = mock(Stop.class);
        when(campanella.getId()).thenReturn(360004869);
        Set<Trip> trips = staticAPI.getTripsFromStop(campanella);

        assertFalse(trips.isEmpty());
        assertTrue(trips.stream().anyMatch(t -> t.getNumberSign().equals("2732-10")));
    }

    @Test
    public void getStopsFromTrip() throws Exception {
        Trip usp8022 = mock(Trip.class);
        when(usp8022.getGtfsId()).thenReturn("8022-10-0");

        List<Stop> stops = staticAPI.getStopsFromTrip(usp8022);
        assertFalse(stops.isEmpty());
    }

    @Test
    public void getTrip() throws Exception {
        Trip bonifacio = staticAPI.getTrip("273L-10", SECONDARY_TERMINAL);
        assertTrue(equalsIgnoreCase(bonifacio.getDestinationSign(), "Metrô Artur Alvim"));
        assertTrue(contains(bonifacio.getNumberSign(), "273L-10"));
        assertEquals(bonifacio.getHeading(), Heading.SECONDARY_TERMINAL);
    }

    @Test
    public void getAllCorridors() throws Exception {
        List<Corridor> corridors = staticAPI.getAllCorridors();
        assertTrue(corridors.size() > 0);
    }

    @Test
    public void getCorridorByTerm() throws Exception {
        Corridor corridor =  staticAPI.getCorridorByTerm("campo limpo");
        assertEquals(8, corridor.getId());
    }

    @Test
    public void getStopsFromCorridor() throws Exception {
        Corridor campoLimpo = mock(Corridor.class);
        when(campoLimpo.getId()).thenReturn(8);
        List<Stop> stops = staticAPI.getStopsFromCorridor(campoLimpo);

        assertFalse(stops.isEmpty());
        assertTrue(stops.stream().allMatch(stop -> stop.getName() != null));
    }

    @Test
    public void getAllBusLanes() throws Exception {
        List<BusLane> lanes = staticAPI.getAllBusLanes();
        assertFalse(lanes.isEmpty());
    }

    @Test
    public void getBusLanesByTerm() throws Exception {
        List<BusLane> lanes = staticAPI.getBusLanesByTerm("haia");
        assertFalse(lanes.isEmpty());
    }

}