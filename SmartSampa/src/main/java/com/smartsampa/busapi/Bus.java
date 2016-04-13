package com.smartsampa.busapi;

import com.smartsampa.utils.Point;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
        return new ToStringBuilder(this)
                .append("prefix", prefix)
                .append("wheelChairCapable", wheelChairCapable)
                .append("location", location)
                .toString();
    }
}
