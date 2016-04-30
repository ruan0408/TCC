package com.smartsampa.busapi2.model;

/**
 * Created by ruan0408 on 16/04/2016.
 */
public enum Heading {
    MAIN_TERMINAL, SECONDARY_TERMINAL;

    public static Heading reverse(Heading h) {
        if (h == MAIN_TERMINAL)
            return SECONDARY_TERMINAL;

        return MAIN_TERMINAL;
    }

    public static Heading getHeadingFromInt(int heading) {
        if (heading == 1)
            return SECONDARY_TERMINAL;

        return MAIN_TERMINAL;
    }
}
