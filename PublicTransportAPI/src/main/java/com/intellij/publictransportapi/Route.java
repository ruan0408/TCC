package com.intellij.publictransportapi;


import com.intellij.olhovivoapi.BusLine;
import com.intellij.openapi.util.Pair;
import org.apache.commons.lang.text.StrBuilder;
import org.onebusaway.gtfs.model.FareRule;

import java.util.function.Predicate;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class Route {

    private String numberSign;
    private int type;
    private boolean circular;
    private String info;
    private Trip mtst;
    private Trip stmt;

    private Route(){}

    protected Route(String numberSign, int type, boolean circular, String info) {
        this.numberSign = numberSign;
        this.type = type;
        this.circular = circular;
        this.info = info;
    }

    public void setMTST(Trip mtst) {this.mtst = mtst;}

    public void setSTMT(Trip stmt) {this.stmt = stmt;}

    public void setNumberSign(String numberSign) {this.numberSign = numberSign;}

    public void setType(int type) {this.type = type;}

    public void setCircular(boolean circular) {this.circular = circular;}

    public void setInfo(String info) {this.info = info;}

    public String getNumberSign() {return numberSign;}

    public int getType() {return type;}

    public boolean isCircular() {return circular;}

    public String getInfo() {return info;}

    public Trip getTripMTST() {return mtst;}

    public Trip getTripSTMT() {return stmt;}

    public String fullNumberSign() {return numberSign+"-"+type;}

    public double getFarePrice() {
        Predicate<FareRule> predicate;
        predicate = f -> f.getRoute().getId().getId().equals(fullNumberSign());

        FareRule rule = API.filterGtfsToElement("getAllFareRules", predicate);

        return rule.getFare().getPrice();
    }

    protected static Route buildFrom(String fullNumberSign) {
        Route newRoute = new Route();
        Pair<BusLine, BusLine> bothTrips = API.getBothTrips(fullNumberSign);

        newRoute.setNumberSign(fullNumberSign.substring(0,3));
        newRoute.setType(Integer.parseInt(fullNumberSign.substring(5)));
        newRoute.setCircular(bothTrips.first.isCircular());
        newRoute.setInfo(bothTrips.first.getInfo());

        Trip mtst = new Trip();
        mtst.setInternalId(bothTrips.first.getCode());
        mtst.setDestinationSign(bothTrips.first.getDestinationSignMTST());
        mtst.setRoute(newRoute);

        Trip stmt = new Trip();
        stmt.setInternalId(bothTrips.second.getCode());
        stmt.setDestinationSign(bothTrips.second.getDestinationSignSTMT());
        stmt.setRoute(newRoute);

        newRoute.setMTST(mtst);
        newRoute.setSTMT(stmt);
        return newRoute;
    }

    protected String basicToString() {
        StrBuilder builder = new StrBuilder();
        builder.appendln("numberSign: "+numberSign);
        builder.appendln("type: "+type);
        builder.appendln("circular: "+circular);
        builder.appendln("info: "+info);

        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Route)) return false;

        Route that = (Route) obj;
        return this.numberSign.equals(that.numberSign);
    }

    @Override
    public String toString() {
        StrBuilder builder = new StrBuilder();
        builder.append(basicToString());

        if (mtst != null) builder.appendln("mtst: "+mtst.getDestinationSign());
        else builder.appendln("mtst: "+null);

        if (stmt != null) builder.appendln("stmt: "+stmt.getDestinationSign());
        else builder.appendln("stmt: "+null);

        return builder.toString();
    }
}
