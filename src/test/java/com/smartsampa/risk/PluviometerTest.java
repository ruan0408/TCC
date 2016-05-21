package com.smartsampa.risk;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ruan0408 on 8/04/2016.
 */
public class PluviometerTest {

    @Test
    public void getAllPluviometers() {
        List<Pluviometer> pluviometers = Pluviometer.getAllPluviometers();
        assertNotNull(pluviometers);
        assertFalse(pluviometers.isEmpty());
    }
}