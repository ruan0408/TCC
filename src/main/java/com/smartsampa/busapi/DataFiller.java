package com.smartsampa.busapi;

import com.smartsampa.gtfswrapper.GtfsAPIFacade;
import com.smartsampa.olhovivoapi.OlhovivoAPI;
import com.smartsampa.utils.APIConnectionException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by ruan0408 on 16/07/2016.
 */
public class DataFiller {

    private OlhovivoAPI olhovivoAPI;
    private GtfsAPIFacade gtfsAPIFacade;

    public DataFiller() {
        olhovivoAPI = Provider.getOlhovivoAPI();
        gtfsAPIFacade = Provider.getGtfsAPIFacade();
    }

    public void fill(Trip trip) {
        Trip olhovivoTrip = olhovivoAPI.getTrip(trip);
        Trip gtfsTrip = gtfsAPIFacade.getTrip(trip);

        Trip fullTrip = merge(Trip.class, gtfsTrip, olhovivoTrip);
        merge(Trip.class, trip, fullTrip);
    }

    public void fill(Stop stop) {
        Stop olhovivoStop = olhovivoAPI.getStop(stop);
        Stop gtfsStop = gtfsAPIFacade.getStop(stop);

        Stop fullStop = merge(Stop.class, gtfsStop, olhovivoStop);
        merge(Stop.class, stop, fullStop);
    }

    private  <T> T merge(Class<T> tClass, T thisObject, T thatObject) {
        try {
            if (thisObject == null && thatObject == null)
                return (T) tClass.getMethod("empty" + tClass.getSimpleName()).invoke(null);
            if (thisObject == null) return thatObject;
            if (thatObject == null) return thisObject;

            for (Field field : tClass.getDeclaredFields()) {
                Method getterMethod = getGetterMethod(field.getName(), tClass);
                Method setterMethod = getSetterMethod(field.getName(), tClass);

                Object thisValue = getterMethod.invoke(thisObject);
                Object thatValue = getterMethod.invoke(thatObject);

                if (thisValue == null) setterMethod.invoke(thisObject, thatValue);
            }
        } catch (IllegalAccessException | IntrospectionException | InvocationTargetException | NoSuchMethodException e) {
            throw new APIConnectionException("An internal unexpected error occurred", e);
        }
        return thisObject;
    }

    private <T> Method getGetterMethod(String field, Class<T> tClass) throws IntrospectionException {
        PropertyDescriptor descriptor = new PropertyDescriptor(field, tClass);
        return descriptor.getReadMethod();
    }

    private <T> Method getSetterMethod(String field, Class<T> tClass) throws IntrospectionException {
        PropertyDescriptor descriptor = new PropertyDescriptor(field, tClass);
        return descriptor.getWriteMethod();
    }
}
