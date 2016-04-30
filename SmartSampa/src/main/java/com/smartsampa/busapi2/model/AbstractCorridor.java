package com.smartsampa.busapi2.model;

import com.smartsampa.busapi2.impl.BusAPIManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ruan0408 on 29/04/2016.
 */
public abstract class AbstractCorridor implements Corridor {

    public static List<Corridor> getAllCorridors() {
        return BusAPIManager.olhovivo.getAllCorridors();
    }

    public static Corridor getCorridorByTerm(String term) {
        return getAllCorridors().stream()
                .filter(c -> StringUtils.containsIgnoreCase(c.getName(), term))
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Stop> getStops() {
        List<Stop> stops = BusAPIManager.olhovivo.getStopsByCorridor(getId());
        return stops.stream()
                .map(Stop::getId)
                .map(AbstractStop::getStopById)
                .collect(Collectors.toList());
    }

    @Override
    public int hashCode() {
        int result = 17;
        return 31 * result + getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractCorridor)) return false;

        AbstractCorridor that = (AbstractCorridor) obj;
        return this.getId() == that.getId();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(getId())
                .append(getCodCot())
                .append(getName())
                .append(getStops())
                .toString();
    }
}
