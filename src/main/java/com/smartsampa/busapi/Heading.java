package com.smartsampa.busapi;

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

    public static int getIntFromHeading(Heading heading) {
        if (heading == SECONDARY_TERMINAL)
            return 1;

        return 2;
    }
}
