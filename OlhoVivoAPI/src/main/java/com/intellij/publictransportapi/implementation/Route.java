package com.intellij.publictransportapi.implementation;


import org.apache.commons.lang.text.StrBuilder;

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

    public Route(String numberSign, int type, boolean circular, String info) {
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

    /* METHODS THAT WILL USE THE GTFS*/

    public double getFarePrice() {return 0;}

    public String getLongName() {return null;}

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Route)) return false;

        Route that = (Route) obj;
        return this.numberSign.equals(that.numberSign);
    }

    public String basicToString() {
        StrBuilder builder = new StrBuilder();
        builder.appendln("numberSign: "+numberSign);
        builder.appendln("type: "+type);
        builder.appendln("circular: "+circular);
        builder.appendln("info: "+info);

        return builder.toString();
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
