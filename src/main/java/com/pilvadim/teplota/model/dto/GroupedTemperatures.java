package com.pilvadim.teplota.model.dto;

import com.pilvadim.teplota.model.Place;

public class GroupedTemperatures {

    private Integer id;
    private String name;

    private LocationTemperatures temperatures;

    public GroupedTemperatures( Place pl ) {

        this.id = pl.getId();
        this.name = pl.getName();
        this.temperatures = new LocationTemperatures();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationTemperatures getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(LocationTemperatures temperatures) {
        this.temperatures = temperatures;
    }
}
