package com.smartsampa.utils;

/**
 * Created by ruan0408 on 27/02/2016.
 */
public class APIConnectionException extends RuntimeException {

    public APIConnectionException(String message) {
        super(message);
    }

    public APIConnectionException(String message, Exception e) {
        super(message, e);
    }

}
