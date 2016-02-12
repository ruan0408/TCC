package com.intellij.smartcity;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ruan0408 on 11/02/2016.
 */
public class OlhoVivoAPIWrapperTest {

    private OlhoVivoAPIWrapper api;

    @Test
    public void testAuthenticate() {
        api = new OlhoVivoAPIWrapper("3de5ce998806e0c0750b1434e17454b6490ccf0a595f3884795da34460a7e7b3");
        boolean resp = api.authenticate();
        Assert.assertTrue(resp);
    }

//    @Test
//    public void testAuthenticate2() throws Exception {
//        boolean resp = api.authenticate2();
//        Assert.assertTrue(resp);
//    }

//    @Test
//    public void testGetLinha() throws Exception {
//        String linhas = api.getLinhas("Lapa");
//    }
}