package com.intellij.utils;

import java.io.IOException;

/**
 * Created by ruan0408 on 27/02/2016.
 */
public class APIConnectionException extends IOException {

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
