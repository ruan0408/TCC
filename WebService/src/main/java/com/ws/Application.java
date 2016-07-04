package com.ws;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.smartsampa.busapi.BusAPI;
import com.smartsampa.busapi.Stop;
import com.smartsampa.busapi.Trip;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

/**
 * Created by ruan0408 on 9/04/2016.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        BusAPI.setSptransLogin("ruan0408");
        BusAPI.setSptransPassword("costaruan");
        BusAPI.setOlhovivoKey("3de5ce998806e0c0750b1434e17454b6490ccf0a595f3884795da34460a7e7b3");
        BusAPI.initialize();
    }

    @Bean
    public ObjectMapper jacksonMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        mapper.setVisibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY);

        mapper.addMixIn(Trip.class, TripMixin.class);
        mapper.addMixIn(Stop.class, StopMixin.class);

        SimpleModule module = new SimpleModule();
        module.addKeySerializer(Trip.class, new TripKeySerializer());
        module.addKeySerializer(Stop.class, new StopKeySerializer());
        mapper.registerModule(module);

        return mapper;
    }

    private class TripKeySerializer extends JsonSerializer<Trip> {
        @Override
        public void serialize(Trip trip, JsonGenerator jgen, SerializerProvider provider)
                throws IOException {
            jgen.writeFieldName(trip.getId());
        }
    }

    private class StopKeySerializer extends JsonSerializer<Stop> {
        @Override
        public void serialize(Stop stop, JsonGenerator jgen, SerializerProvider provider)
                throws IOException {
            jgen.writeFieldName(String.valueOf(stop.getId()));
        }
    }
}