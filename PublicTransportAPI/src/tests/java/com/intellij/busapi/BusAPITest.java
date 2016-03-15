package com.intellij.busapi;

import org.junit.BeforeClass;

/**
 * Created by ruan0408 on 13/03/2016.
 */
public class BusAPITest {

    @BeforeClass
    public static void setUp() throws Exception {
        BusAPIManager.getInstance()
                .setSptransLogin("ruan0408")
                .setSptransPassword("costaruan")
                .setOlhovivoKey("3de5ce998806e0c0750b1434e17454b6490ccf0a595f3884795da34460a7e7b3");
        BusAPIManager.getInstance().init();
    }
}
