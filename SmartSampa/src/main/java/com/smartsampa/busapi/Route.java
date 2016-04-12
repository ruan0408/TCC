package com.smartsampa.busapi;


import org.apache.commons.lang3.text.StrBuilder;

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

    private RouteFacade routeFacade;

    protected Route() {
        routeFacade = new RouteFacade();
    }

    protected Route(String numberSign, int type, boolean circular, String info) {
        this();
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
        return routeFacade.getFarePrice(fullNumberSign());
    }

    public static Route buildFrom(String fullNumberSign) {
        return RouteFacade.buildFrom(fullNumberSign);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Route)) return false;

        Route that = (Route) obj;
        return this.fullNumberSign().equals(that.fullNumberSign());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + numberSign.hashCode();
        result = 31 * result + type;
        return result;
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

    protected String basicToString() {
        StrBuilder builder = new StrBuilder();
        builder.appendln("numberSign: "+numberSign);
        builder.appendln("type: "+type);
        builder.appendln("circular: "+circular);
        builder.appendln("info: "+info);

        return builder.toString();
    }
}
