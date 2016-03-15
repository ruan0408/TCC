package com.intellij.plugin;

import com.intellij.utils.APIConnectionException;

/**
 * Created by ruan0408 on 29/02/2016.
 */
public class PublicTransportConnector {

    public void sendRealTimeBuses() throws APIConnectionException {
        PublicTransportAPI.init("3de5ce998806e0c0750b1434e17454b6490ccf0a595f3884795da34460a7e7b3",
                "ruan0408", "costaruan");
//        PublicTransportAPI.
    }
}
