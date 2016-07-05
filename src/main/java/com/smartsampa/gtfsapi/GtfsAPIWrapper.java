package com.smartsampa.gtfsapi;

import com.smartsampa.busapi.Heading;
import com.smartsampa.busapi.Stop;
import com.smartsampa.busapi.Trip;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * Created by ruan0408 on 3/07/2016.
 */
public class GtfsAPIWrapper {

    private GtfsAPI gtfsAPI;

    public GtfsAPIWrapper(GtfsAPI gtfsAPI) {
        this.gtfsAPI = gtfsAPI;
    }

    public Set<Trip> getTripsByTerm(String term) {
        return gtfsAPI.getTripsWithRouteContaining(term).stream()
                .flatMap(trip -> Stream.of(new GtfsTrip(trip), getTheOtherTrip(trip)))
                .filter(trip -> trip.containsTerm(term))
                .collect(toSet());
    }

    public Set<Stop> getStopsByTerm(String term) {
        return gtfsAPI.getStopsByTerm(term).stream()
                .map(GtfsStop::new)
                .collect(toSet());
    }

    public Set<Trip> getTripsFromStop(Stop stop) {
        return gtfsAPI.getAllTripsFromStopId(stop.getId()).stream()
                .map(GtfsTrip::new)
                .collect(toSet());
    }

    public List<Stop> getStopsFromTrip(Trip trip) {
        return gtfsAPI.getAllStopsOrderedFromTripId(trip.getGtfsId()).stream()
                .map(GtfsStop::new)
                .collect(toList());
    }

    public Stop getStopById(int id) {
        return new GtfsStop(gtfsAPI.getStopById(id));
    }

    /*
    * This method creates a clone of this GtfsTrip, but changes the heading
    * and the destinationSign. It's necessary because the gtfs files have only one entry
    * for circular trips, while the Olhovivo API has two entries for circular trips.
    * Therefore we "falsify" one of the sides of the trip.
    * */
    private GtfsTrip getTheOtherTrip(org.onebusaway.gtfs.model.Trip gtfsRawTrip) {
        return new GtfsTrip (gtfsRawTrip) {
            @Override
            public Heading getHeading() {
                return Heading.reverse(super.getHeading());
            }

            @Override
            public String getDestinationSign() {
                String oldSign = super.getDestinationSign();
                String longName = gtfsRawTrip.getRoute().getLongName();

                String newSign = longName.replace(oldSign, "").trim();
                return newSign.replaceAll("^-|-$", "").trim();
            }
        };
    }
}
