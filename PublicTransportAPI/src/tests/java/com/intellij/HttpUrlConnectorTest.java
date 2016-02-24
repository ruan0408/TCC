package com.intellij;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by ruan0408 on 23/02/2016.
 */
public class HttpUrlConnectorTest {

    private HttpUrlConnector connector;
    @Before
    public void setUp() throws Exception {
        connector = new HttpUrlConnector();
    }

    @Test
    public void testDownloadGtfs() throws Exception {
        connector.downloadGtfs();
    }
}