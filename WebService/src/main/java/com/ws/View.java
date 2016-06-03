package com.ws;

/**
 * Created by ruan0408 on 21/05/2016.
 */
public class View {
    interface TripSummary {}
    interface TripComplete extends TripSummary, StopSummary {}

    interface StopSummary {}
    interface StopComplete extends StopSummary, TripSummary {}

    interface CorridorComplete extends StopSummary {}

    interface NotIncluded {}
}
