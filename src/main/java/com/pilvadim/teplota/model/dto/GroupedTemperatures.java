package com.pilvadim.teplota.model.dto;

import java.util.List;

public class GroupedTemperatures {

    List<LocationTemperatures> locations;

    public List<LocationTemperatures> getLocations() {
        return locations;
    }

    public void setLocations(List<LocationTemperatures> locations) {
        this.locations = locations;
    }
}
