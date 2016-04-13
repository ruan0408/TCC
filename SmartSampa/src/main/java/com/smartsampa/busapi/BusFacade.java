package com.smartsampa.busapi;

import com.smartsampa.olhovivoapi.OlhovivoBus;
import com.smartsampa.utils.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruan0408 on 9/04/2016.
 */
public class BusFacade {

    protected static List<Bus> olhovivoBusArrayToBuses(OlhovivoBus[] busArray) {
        return convertAnyOlhovivoBusToAnyAPIBus(busArray, Bus.class);
    }

    protected static <K extends OlhovivoBus, T extends Bus>
    List<T> convertAnyOlhovivoBusToAnyAPIBus(K[] buses, Class<T> tClass) {
        if (buses == null) return new ArrayList<>();

        List<T> tList = Utils.convertKArrayToTList(buses, kBus -> buildFromAPIBus(kBus, tClass));
        return tList;
    }

    private static <T extends Bus> T buildFromAPIBus(OlhovivoBus bus, Class<T> tClass) {
        T newBus = null;
        try {
            newBus = tClass.newInstance();
            newBus.setPrefix(bus.getPrefixNumber());
            newBus.setWheelChairCapable(bus.isWheelChairCapable());
            newBus.setLocation(new Point(bus.getLatitude(), bus.getLongitude()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newBus;
    }
}
