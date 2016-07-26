package com.smartsampa.busapi;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by ruan0408 on 12/04/2016.
 */
public abstract class Stop {

    private Integer id;
    private String name;
    private String address;
    private String reference;
    private Double latitude;
    private Double longitude;

    public static Stop emptyStop() { return NullStop.getInstance(); }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() {return address;}
    public void setAddress(String address) { this.address = address; }

    public String getReference() {return reference;}
    public void setReference(String reference) { this.reference = reference; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Stop)) return false;

        Stop that = (Stop) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getId();
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", getId())
                .append("name", getName())
                .append("address", getAddress())
                .append("reference", getReference())
                .append("latitude", getLatitude())
                .append("longitude", getLongitude())
                .toString();
    }
}
