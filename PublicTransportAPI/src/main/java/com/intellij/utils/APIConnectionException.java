package com.intellij.utils;

/**
 * Created by ruan0408 on 27/02/2016.
 */
public class APIConnectionException extends RuntimeException {

    public APIConnectionException(String message) {
        super(message);
    }

    public static APIConnectionException throwOlhoVivoConnectionException() {
        return new APIConnectionException("A problem happened when connecting to the Olho Vivo API");
    }

    public static APIConnectionException throwGTFSConnectionException() {
        return new APIConnectionException("The GTFS folder was not found");
    }
}
