package com.intellij.publictransportapi;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

/**
 * Created by ruan0408 on 18/02/2016.
 */
public class APITest {

    public static Trip alvim, bonifacio, usp, butanta;
    public static Route route273l, route8012;
    public static Stop campanella, brasiliana;
    public static Corridor campoLimpo;

    @BeforeClass
    public static void setUp() throws Exception {
        API.init("3de5ce998806e0c0750b1434e17454b6490ccf0a595f3884795da34460a7e7b3",
                "ruan0408", "costaruan");
        loadTestObjects();
    }

    public static void loadTestObjects() {
        route273l = new Route("273L", 10, false, null);
        route8012 = new Route("8012", 10, true, null);

        alvim = new Trip();
        alvim.setInternalId(224);
        alvim.setDestinationSign("Metrô Artur Alvim");
        alvim.setRoute(route273l);

        bonifacio = new Trip();
        bonifacio.setInternalId(32992);
        bonifacio.setDestinationSign("Cptm José Bonifácio.");
        bonifacio.setRoute(route273l);

        route273l.setMTST(bonifacio);
        route273l.setSTMT(alvim);

        usp = new Trip();
        usp.setInternalId(2023);
        usp.setDestinationSign("Cidade Universitária");
        usp.setRoute(route8012);

        butanta = new Trip();
        butanta.setInternalId(34791);
        butanta.setDestinationSign("METRÔ BUTANTÃ");
        butanta.setRoute(route8012);

        route8012.setMTST(usp);
        route8012.setSTMT(butanta);

        campanella = new Stop(360004869,
                "Av. Dos Campanellas", new Point(-23.536056,-46.467776));

        brasiliana = new Stop(120010354,
                "Biblioteca Brasiliana", new Point(-23.562368,-46.723045));

        campoLimpo = new Corridor(8, "Campo Limpo");
    }

    @Test
    public void testSearchTrip() throws Exception {
        List<Trip> tripList = API.searchTrip("273L");

        Assert.assertTrue(tripList.size() == 2);
        Assert.assertTrue(tripList.contains(alvim));
        Assert.assertTrue(tripList.contains(bonifacio));

        tripList = API.searchTrip("8012");
        Assert.assertTrue(tripList.size() == 2);
        Assert.assertTrue(tripList.contains(usp));
        Assert.assertTrue(tripList.contains(butanta));
    }

    @Test
    public void testGetAllCorridors() throws Exception {
        List<Corridor> allCorridors = API.getAllCorridors();
        Assert.assertEquals(7, allCorridors.size());
    }
}