package com.intellij.busapi;

import org.apache.commons.lang.text.StrBuilder;

/**
 * Created by ruan0408 on 17/02/2016.
 */
public class Bus {

    private String prefix;
    private boolean wheelChairCapable;
    private Point location;

    public String getPrefix() {return prefix;}

    public boolean isWheelChairCapable() {return false;}

    public Point getLocation() {return null;}

    public void setPrefix(String prefix) {this.prefix = prefix;}

    public void setWheelChairCapable(boolean wheelChairCapable) {this.wheelChairCapable = wheelChairCapable;}

    public void setLocation(Point location) {this.location = location;}

    @Override
    public String toString() {
        StrBuilder builder = new StrBuilder();
        builder.appendln("prefix: "+prefix);
        builder.appendln("wheelChairCapable: "+wheelChairCapable);
        builder.append("location: "+location.toString());
        return builder.toString();
    }
}
